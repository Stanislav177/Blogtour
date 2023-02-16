package com.myblogtour.blogtour.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.myblogtour.airtable.data.RepoAirTableImpl
import com.myblogtour.airtable.domain.PublicationDTO
import com.myblogtour.airtable.domain.UserProfileDTO
import com.myblogtour.blogtour.appState.AppStateListBlog
import com.myblogtour.blogtour.utils.converterFromDtoToPublicationEntity
import com.myblogtour.blogtour.utils.converterFromProfileUserDtoToProfileUserEntity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeViewModel(private val liveData: MutableLiveData<AppStateListBlog> = MutableLiveData()) :
    ViewModel() {

    private val repoAirTable: RepoAirTableImpl by lazy { RepoAirTableImpl() }
    private val user by lazy { Firebase.auth.currentUser }
    private lateinit var idLikeLocal: String

    fun getLiveData() = liveData

    fun getPostList() {
        repoAirTable.getPublication(callback)
    }

    private val callback = object : Callback<PublicationDTO> {
        override fun onResponse(call: Call<PublicationDTO>, response: Response<PublicationDTO>) {
            if (response.isSuccessful) {
                if (response.body() == null) {
                    //Error
                } else {
                    response.body()?.let {
                        liveData.postValue(AppStateListBlog.Success(
                            converterFromDtoToPublicationEntity(getIdUser(user), it)))
                    }
                }
            }
        }

        override fun onFailure(call: Call<PublicationDTO>, t: Throwable) {
            val t = t.message
        }
    }

    private fun getIdUser(user: FirebaseUser?): String {
        user?.let {
            return it.displayName!!
        }
        return ""
    }

    fun likePublication(idLike: String, like: Boolean) {
        idLikeLocal = idLike
        loadListLikeUser()
    }

    private fun loadListLikeUser() {
        val formulaUid = "uid="
        val uid = user!!.uid
        val requestProfile = "$formulaUid'$uid'"
        repoAirTable.getUserProfile(requestProfile, callbackLoadingProfile)
    }

    private val callbackLoadingProfile = object : Callback<UserProfileDTO> {
        override fun onResponse(
            call: Call<UserProfileDTO>,
            response: Response<UserProfileDTO>,
        ) {
            if (response.isSuccessful) {
                response.body()?.let {
                    listLikeUser(it)
                }
            }
        }

        override fun onFailure(call: Call<UserProfileDTO>, t: Throwable) {
            val ex = t.message
        }

    }

    private fun listLikeUser(it: UserProfileDTO) {
        val user = converterFromProfileUserDtoToProfileUserEntity(it)
        val idUserProfile = user!!.id
        var listLike: MutableList<String> = mutableListOf()

        user?.let {
            it.likePublication?.let { list ->
                listLike = list.toMutableList()
            }
        }
        val search = listLike.binarySearch(idLikeLocal)

        if (search >= 0) {
            listLike.apply {
                remove(idLikeLocal)
                updateProfileListLike(idUserProfile, this)
            }
        } else if (listLike.size == 0) {
            listLike.apply {
                add(idLikeLocal)
                updateProfileListLike(idUserProfile, this)
            }
        } else {
            listLike.apply {
                add(listLike.size, idLikeLocal)
                updateProfileListLike(idUserProfile, this)
            }
        }
    }

    private fun updateProfileListLike(idUserProfile: String, listLike: MutableList<String>) {
        val listArray = JsonArray()
        val listFields = JsonObject()
        val listLikePublication = JsonObject()
        val sizeList = listLike.size
        for (i in 0 until sizeList) {
            listArray.add(listLike[i])
        }
        listLikePublication.add("likepublication", listArray)
        listFields.add("fields", listLikePublication)

        repoAirTable.updateUserProfileLikeCounter(idUserProfile, listFields, callbackUpdate)
    }

    private val callbackUpdate = object : Callback<Unit> {
        override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
            if (response.isSuccessful) {
                val res = response.body()
            }
        }

        override fun onFailure(call: Call<Unit>, t: Throwable) {
            val ex = t.message
        }
    }
}
