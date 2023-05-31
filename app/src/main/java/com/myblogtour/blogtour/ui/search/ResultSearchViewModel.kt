package com.myblogtour.blogtour.ui.search

import android.text.Editable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.myblogtour.blogtour.appState.AppStateSearchPublication
import com.myblogtour.blogtour.domain.repository.AuthFirebaseRepository
import com.myblogtour.blogtour.domain.repository.SearchPublication
import com.myblogtour.blogtour.utils.converterFromDtoToPublicationEntity

class ResultSearchViewModel(
    private val searchPublication: SearchPublication,
    private val authFirebaseRepository: AuthFirebaseRepository,
    private val liveData: MutableLiveData<AppStateSearchPublication> = MutableLiveData(),
) : ViewModel() {

    fun getLiveData() = liveData
    private lateinit var idPublicationLocal: String

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

    fun setSearchPublication(s: Editable?) {
        if (s != null) {
            if (s.toString() != "") {
                liveData.postValue(AppStateSearchPublication.OpenSearchFragment(true))
                getSearchPublication(converterTextSearch(s.toString()))
            } else {
                liveData.postValue(AppStateSearchPublication.OnError("Введите данные"))
            }
        } else {
            liveData.postValue(AppStateSearchPublication.OnError("Введите данные"))
        }
    }

    private fun converterTextSearch(s: String) = "location=\"$s\""

    private fun getSearchPublication(search: String) {
        searchPublication.getSearchPublication(search,
            onSuccess = {
                if (it.records.isNotEmpty()) {
                    liveData.postValue(
                        AppStateSearchPublication.OnSuccess(
                            converterFromDtoToPublicationEntity(checkAuthUser(), getIdUser(), it)
                        )
                    )
                } else {
                    liveData.postValue(AppStateSearchPublication.OnError("Данных нет"))
                }
            },
            onError = {
                val e = it
            })
    }

    fun updatePublicationComplaint(idPublication: String, complaintId: String) {
        val complaintIdArray = JsonArray()
        complaintIdArray.add(complaintId)
        val complaint = JsonObject()
        complaint.add("Complaint", complaintIdArray)
        val updateFieldsComplaint = JsonObject()
        updateFieldsComplaint.add("fields", complaint)
        searchPublication.updateComplaintPublication(idPublication, updateFieldsComplaint)
            .subscribe()
    }

    fun likePublication(idPublication: String) {
        checkAuthUser()
        idPublicationLocal = idPublication
        loadListLikeUser()
    }

    private fun loadListLikeUser() {
        searchPublication.loadingUserProfile(
            getIdUser(),
            onSuccess = {
                listLikeUser(it.fields.likePublication)
            },
            onError = {
                liveData.postValue(AppStateSearchPublication.OnErrorLike("Что-то пошло не так"))
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
            searchPublication.clickCounterLikePublication(getIdUser()!!, listFields)
        } catch (e: Throwable) {
            liveData.postValue(AppStateSearchPublication.OnErrorLike("Что-то пошло не так"))
        }
    }
}