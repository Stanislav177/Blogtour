package com.myblogtour.blogtour.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.myblogtour.airtable.BuildConfig
import com.myblogtour.blogtour.appState.AppStateListBlog
import com.myblogtour.blogtour.domain.repository.AuthFirebaseRepository
import com.myblogtour.blogtour.domain.repository.PublicationRepository
import com.myblogtour.blogtour.utils.converterFromDtoToPublicationEntity
import io.reactivex.rxjava3.kotlin.subscribeBy


class HomeViewModel(
    private val publicationRepository: PublicationRepository,
    private val authFirebaseRepository: AuthFirebaseRepository,
    private val liveData: MutableLiveData<AppStateListBlog> = MutableLiveData(),
) : ViewModel() {

    private lateinit var idPublicationLocal: String

    fun getLiveData() = liveData

    private fun checkAuthUser(): Boolean {
        var userCurrent = false
        authFirebaseRepository.userCurrent(
            onSuccess = {
                userCurrent = true
            },
            onError = {
                userCurrent = false
            }
        )
        return userCurrent
    }

    fun getPostList() {
        publicationRepository.getPublication().subscribeBy(
            onSuccess = {
                liveData.postValue(
                    AppStateListBlog.Success(
                        converterFromDtoToPublicationEntity(checkAuthUser(), getIdUser(), it)
                    )
                )
            },
            onError = {
                liveData.postValue(AppStateListBlog.Error(it))
            }
        )
    }

    private fun getIdUser(): String? {
        var idUser: String? = null
        authFirebaseRepository.userCurrent(
            onSuccess = {
                idUser = it.displayName
            },
            onError = {

            }
        )
        return idUser
    }

    fun likePublication(idPublication: String) {
        checkAuthUser()
        idPublicationLocal = idPublication
        loadListLikeUser()
    }

    private fun loadListLikeUser() {
        publicationRepository.loadingUserProfile(
            getIdUser(),
            onSuccess = {
                listLikeUser(it.fields.likePublication)
            },
            onError = {
                liveData.postValue(AppStateListBlog.Error(it))
            }
        )
    }

    private fun listLikeUser(list: List<String>?) {
        var updateListLike: MutableList<String> = mutableListOf()
        list?.let {
            updateListLike = list.toMutableList()
            if (updateListLike.isNullOrEmpty()) {
                updateListLike.add(idPublicationLocal)
            } else {
                updateListLike.let { notNullListLike ->
                    val search = notNullListLike.binarySearch(idPublicationLocal)

                    if (search >= 0) {
                        notNullListLike.apply {
                            remove(idPublicationLocal)
                            updateProfileListLike(updateListLike)
                            return
                        }
                    } else if (updateListLike.size == 0) {
                        notNullListLike.apply {
                            add(idPublicationLocal)
                            updateProfileListLike(updateListLike)
                            return
                        }
                    } else {
                        notNullListLike.apply {
                            add(updateListLike.size, idPublicationLocal)
                            updateProfileListLike(updateListLike)
                            return
                        }
                    }
                }
            }
        }
        updateListLike.add(idPublicationLocal)
        updateProfileListLike(updateListLike)
    }

    private fun updateProfileListLike(listLike: MutableList<String>) {
        val listArray = JsonArray()
        val listFields = JsonObject()
        val listLikePublication = JsonObject()
        val sizeList = listLike.size
        for (i in 0 until sizeList) {
            listArray.add(listLike[i])
        }
        listLikePublication.add("likepublication", listArray)
        listFields.add("fields", listLikePublication)

        try {
            publicationRepository.clickCounterLikePublication(getIdUser()!!, listFields)
        } catch (e: Throwable) {
            liveData.postValue(AppStateListBlog.Error(IllegalStateException("Необходимо авторизоваться")))
        }
    }

    fun updatePublicationComplaint(idPublication: String) {
        val complaintIdArray = JsonArray()
        complaintIdArray.add(BuildConfig.ID_COMPLAINT)
        val complaint = JsonObject()
        complaint.add("Complaint", complaintIdArray)
        val updateFieldsComplaint = JsonObject()
        updateFieldsComplaint.add("fields", complaint)
        publicationRepository.updateComplaintPublication(idPublication, updateFieldsComplaint)
    }
}
