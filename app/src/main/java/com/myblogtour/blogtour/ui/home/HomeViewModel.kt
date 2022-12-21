package com.myblogtour.blogtour.ui.home

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonObject
import com.myblogtour.airtable.data.RepoAirTablePostingImpl
import com.myblogtour.airtable.domain.DTO
import com.myblogtour.airtable.domain.Record
import com.myblogtour.blogtour.appState.AppStateListBlog
import com.myblogtour.blogtour.data.RepoPostListImpl
import com.myblogtour.blogtour.utils.converterFromDTOtoPost
import okhttp3.OkHttpClient
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

    private var okHttpClient: OkHttpClient? = null


    fun getLiveData() = liveData

    fun getPostList() {
        repoAirTable.getPostingAirTable(callback)
        createPost(111, "NEW", "NEW", 121545, "NEW", "NEW", "NEW")
    }

    private fun createPost(
        id: Int,
        nickname: String,
        text: String,
        likeCount: Int,
        location: String,
        dateTour: String,
        dateAddition: String,
    ) {
        val createPost = JsonObject()
        val post = JsonObject()
        post.addProperty("nickName", nickname)
        post.addProperty("id", id)
        post.addProperty("text", text)
        post.addProperty("likeCount", likeCount)
        post.addProperty("dateTour", dateTour)
        post.addProperty("dateAddition", dateAddition)
        createPost.add("fields", post)

        repoAirTable.createPostAirTable(createPost, callbackPost)
    }

    private val callbackPost = object : Callback<Record> {
        override fun onResponse(call: Call<Record>, response: Response<Record>) {
            if (response.isSuccessful) {
                if (response == null) {
                    //error
                } else {
                    response.body()?.let {
                        val responseIt = it.id

                    }
                }
            }
        }

        override fun onFailure(call: Call<Record>, t: Throwable) {
            val t = t.message
        }
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