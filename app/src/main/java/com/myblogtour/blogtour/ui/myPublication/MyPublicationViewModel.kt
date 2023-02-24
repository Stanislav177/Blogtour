package com.myblogtour.blogtour.ui.myPublication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.myblogtour.airtable.data.RepoAirTableImpl
import com.myblogtour.airtable.domain.PublicationDTO
import com.myblogtour.blogtour.domain.PublicationEntity
import com.myblogtour.blogtour.utils.converterFromDtoToPublicationEntity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyPublicationViewModel : ViewModel(), MyPublicationContract.MyPublication {

    override val listPublication: LiveData<List<PublicationEntity>> = MutableLiveData()

    private val repoAirTable: RepoAirTableImpl by lazy {
        RepoAirTableImpl()
    }
    private lateinit var uid: String
    private lateinit var idLikePublication: String

    fun getMyPublication(uidUser: String) {
        uid = uidUser
        repoAirTable.getMyPublication(getQueryUid(uid), callback)
    }

    fun deletePublication(idPublication: String, idLikePublication: String) {
        this.idLikePublication = idLikePublication
        repoAirTable.deletePublication(idPublication, callbackDeletePublication)
    }

    private val callbackDeletePublication = object : Callback<Unit> {
        override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
            if (response.isSuccessful) {
                response.body()?.let {
                    val delete = "Публикация удалена"
                    repoAirTable.deletePublicationLike(idLikePublication, callbackDeleteLikePublication)
                }
            }
        }

        override fun onFailure(call: Call<Unit>, t: Throwable) {
            val ex = t.message
        }
    }

    private val callbackDeleteLikePublication = object : Callback<Unit> {
        override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
            if (response.isSuccessful){
                response.body()?.let {
                   val data = "Данные удалены"
                }
            }
        }

        override fun onFailure(call: Call<Unit>, t: Throwable) {
            val ex = t.message
        }
    }

    private val callback = object : Callback<PublicationDTO> {
        override fun onResponse(call: Call<PublicationDTO>, response: Response<PublicationDTO>) {
            if (response.isSuccessful) {
                response.body()?.let {
                    if (it.records.isNotEmpty()) {
                        listPublication.mutable()
                            .postValue(converterFromDtoToPublicationEntity(uid, it))
                    }
                }
            } else {
                val er = "Публикаций нет"
            }
        }

        override fun onFailure(call: Call<PublicationDTO>, t: Throwable) {
            val ex = t.message
        }

    }

    private fun getQueryUid(uid: String): String {
        val userprofile = "userprofile="
        return "$userprofile'$uid'"
    }

    private fun <T> LiveData<T>.mutable(): MutableLiveData<T> {
        return this as? MutableLiveData<T> ?: throw IllegalStateException("It is not Mutable")
    }

}