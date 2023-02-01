package com.myblogtour.airtable.data

import com.google.gson.JsonObject
import com.myblogtour.airtable.BuildConfig
import com.myblogtour.airtable.domain.DTO
import com.myblogtour.airtable.domain.PublicationDTO
import com.myblogtour.airtable.domain.Records
import com.myblogtour.airtable.domain.RepoAirTable
import com.myblogtour.airtable.domain.retrofit.RetrofitAirTable
import retrofit2.Callback

class RepoAirTablePostingImpl : RepoAirTable {

    private val retrofitAirTable by lazy { RetrofitAirTable.startRetrofit() }

    override fun getPostingAirTable(callback: Callback<DTO>) {
        retrofitAirTable.getPosting(BuildConfig.API_KEY).enqueue(callback)
    }

    override fun createPostAirTable(createPost: JsonObject, callback: Callback<Records>) {
        retrofitAirTable.createPost(BuildConfig.API_KEY, createPost).enqueue(callback)
    }

    override fun getPublication(callback: Callback<PublicationDTO>) {
        retrofitAirTable.getListPublication(BuildConfig.API_KEY).enqueue(callback)
    }
}