package com.myblogtour.blogtour.ui.myPublication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.myblogtour.airtable.domain.PublicationDTO
import com.myblogtour.blogtour.domain.PublicationEntity
import com.myblogtour.blogtour.domain.repository.MyPublicationRepository
import com.myblogtour.blogtour.utils.converterFromDtoToPublicationEntity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyPublicationViewModel(private val myPublicationRepository: MyPublicationRepository) :
    ViewModel(),
    MyPublicationContract.MyPublication {

    override val listPublication: LiveData<List<PublicationEntity>> = MutableLiveData()

    private lateinit var uid: String

    fun getMyPublication(uidUser: String) {
        uid = uidUser
        myPublicationRepository.getMyPublication(
            getQueryUid(uid),
            onSuccess = {
                listPublication.mutable().postValue(converterFromDtoToPublicationEntity("", it))
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

    private fun getQueryUid(uid: String): String {
        val userprofile = "userprofile="
        return "$userprofile'$uid'"
    }

    private fun <T> LiveData<T>.mutable(): MutableLiveData<T> {
        return this as? MutableLiveData<T> ?: throw IllegalStateException("It is not Mutable")
    }

}