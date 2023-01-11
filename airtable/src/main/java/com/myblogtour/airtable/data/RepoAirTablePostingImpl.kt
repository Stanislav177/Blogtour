package com.myblogtour.airtable.data

import com.google.gson.JsonObject
import com.myblogtour.airtable.BuildConfig
import com.myblogtour.airtable.domain.*
import com.myblogtour.airtable.domain.retrofit.API_KEY
import com.myblogtour.airtable.domain.retrofit.RetrofitAirTable
import com.myblogtour.airtable.domain.retrofit.URL_API_BASE
import com.myblogtour.airtable.domain.retrofit.URL_API_END_POINT
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import retrofit2.Callback

class RepoAirTablePostingImpl : RepoAirTablePosting {

    private val retrofitAirTable by lazy { RetrofitAirTable.startRetrofit() }

    override fun getPostingAirTable(callback: Callback<DTO>) {
        retrofitAirTable.getPosting(BuildConfig.API_KEY).enqueue(callback)
    }

    override fun createPostAirTable(createPost: JsonObject, callback: Callback<Record>) {
        retrofitAirTable.createPost(BuildConfig.API_KEY, createPost).enqueue(callback)
    }
}