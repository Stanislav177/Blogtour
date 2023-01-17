package com.myblogtour.blogtour.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonObject
import com.myblogtour.airtable.data.RepoAirTablePostingImpl
import com.myblogtour.airtable.domain.DTO
import com.myblogtour.airtable.domain.Record
import com.myblogtour.blogtour.appState.AppStateListBlog
import com.myblogtour.blogtour.utils.converterFromDTOtoPost
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeViewModel(private val liveData: MutableLiveData<AppStateListBlog> = MutableLiveData()) :
    ViewModel() {

    private val repoAirTable: RepoAirTablePostingImpl by lazy {
        RepoAirTablePostingImpl()
    }

    fun getLiveData() = liveData

    fun getPostList() {
        repoAirTable.getPostingAirTable(callback)
    }

    private val callback = object : Callback<DTO> {
        override fun onResponse(call: Call<DTO>, response: Response<DTO>) {
            if (response.isSuccessful) {
                if (response.body() == null) {
                    //Error
                } else {
                    response.body()?.let {
                        liveData.postValue(AppStateListBlog.Success(converterFromDTOtoPost(it)))
                    }
                }
            }
        }

        override fun onFailure(call: Call<DTO>, t: Throwable) {
            val t = t.message
        }
    }
}