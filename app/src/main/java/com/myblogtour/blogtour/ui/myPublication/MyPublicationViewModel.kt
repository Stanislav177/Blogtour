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

    fun getMyPublication(uidUser: String) {
        uid = uidUser
        repoAirTable.getMyPublication(getQueryUid(uid), callback)
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
            TODO("Not yet implemented")
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