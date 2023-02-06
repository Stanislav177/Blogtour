package com.myblogtour.blogtour.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.myblogtour.airtable.data.RepoAirTableImpl
import com.myblogtour.airtable.domain.PublicationDTO
import com.myblogtour.blogtour.appState.AppStateListBlog
import com.myblogtour.blogtour.utils.converterFromDtoToPublicationEntity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeViewModel(private val liveData: MutableLiveData<AppStateListBlog> = MutableLiveData()) :
    ViewModel() {

    private val repoAirTable: RepoAirTableImpl by lazy {
        RepoAirTableImpl()
    }

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
                            converterFromDtoToPublicationEntity(it)))
                    }
                }
            }
        }

        override fun onFailure(call: Call<PublicationDTO>, t: Throwable) {
            val t = t.message
        }
    }
}