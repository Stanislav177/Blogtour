package com.myblogtour.airtable.data

import com.google.gson.JsonObject
import com.myblogtour.airtable.BuildConfig
import com.myblogtour.airtable.domain.*
import com.myblogtour.airtable.domain.retrofit.RetrofitAirTable
import retrofit2.Callback

class RepoAirTablePostingImpl : RepoAirTable {

    private val retrofitAirTable by lazy { RetrofitAirTable.startRetrofit() }

    override fun getPostingAirTable(callback: Callback<DTO>) {
        retrofitAirTable.getPosting(BuildConfig.API_KEY).enqueue(callback)
    }

    override fun createPostAirTable(createPost: JsonObject, callback: Callback<Record>) {
        retrofitAirTable.createPost(BuildConfig.API_KEY, createPost).enqueue(callback)
    }
}