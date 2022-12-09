package com.example.blogtour.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.airtable.data.DTO
import com.example.airtable.domain.RepoAirTablePostingImpl
import com.example.blogtour.appState.AppStateListBlog
import com.example.blogtour.data.RepoPostListImpl
import com.example.blogtour.utils.converterFromDTOtoPost
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(private val liveData: MutableLiveData<AppStateListBlog> = MutableLiveData()) :
    ViewModel() {

    private val repoPostList: RepoPostListImpl by lazy {
        RepoPostListImpl()
    }

    private val repoAirTable: RepoAirTablePostingImpl by lazy {
        RepoAirTablePostingImpl()
    }

    fun getLiveData() = liveData

    fun getPostList() {
        //liveData.postValue(AppStateListBlog.Success(repoPostList.getPost()))
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