package com.myblogtour.blogtour.ui.myPublication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.myblogtour.blogtour.domain.PublicationEntity
import com.myblogtour.blogtour.domain.repository.AuthFirebaseRepository
import com.myblogtour.blogtour.domain.repository.MyPublicationRepository
import com.myblogtour.blogtour.utils.converterFromDtoToPublicationEntity

class MyPublicationViewModel(
    private val myPublicationRepository: MyPublicationRepository,
    private val authFirebaseRepository: AuthFirebaseRepository
) :
    ViewModel(), MyPublicationContract.MyPublication {

    override val listPublication: LiveData<List<PublicationEntity>> = MutableLiveData()

    fun getMyPublication() {
        authFirebaseRepository.userCurrent(
            onSuccess = {
                val uid = it.uid
                myPublicationRepository.getMyPublication(
                    "userprofile='$uid'",
                    onSuccess = {
                        listPublication.mutable()
                            .postValue(converterFromDtoToPublicationEntity(true, "", it))
                    },
                    onError = {

                    }
                )
            },
            onError = {

            }
        )


    }

    fun deletePublication(idPublication: String, idLikePublication: String) {
        myPublicationRepository.deletePublication(idPublication,
            onSuccess = {
                if (it) {
                    myPublicationRepository.deleteLikePublication(idLikePublication)
                }
            }, onError = {

            }
        )
    }

    private fun <T> LiveData<T>.mutable(): MutableLiveData<T> {
        return this as? MutableLiveData<T> ?: throw IllegalStateException("It is not Mutable")
    }

}