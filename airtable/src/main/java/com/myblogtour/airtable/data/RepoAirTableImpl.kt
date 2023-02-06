package com.myblogtour.airtable.data

import com.google.gson.JsonObject
import com.myblogtour.airtable.BuildConfig
import com.myblogtour.airtable.domain.PublicationDTO
import com.myblogtour.airtable.domain.Record
import com.myblogtour.airtable.domain.RecordUserProfileDTO
import com.myblogtour.airtable.domain.RepoAirTable
import com.myblogtour.airtable.domain.retrofit.RetrofitAirTable
import retrofit2.Callback

class RepoAirTableImpl : RepoAirTable {

    private val retrofitAirTable by lazy { RetrofitAirTable.startRetrofit() }

    override fun createUserProfile(createUserProfile: JsonObject, callback: Callback<RecordUserProfileDTO>) {
        retrofitAirTable.createUserProfile(BuildConfig.API_KEY,createUserProfile).enqueue(callback)
    }

    override fun createPostAirTable(createPost: JsonObject, callback: Callback<Record>) {
        retrofitAirTable.createPublication(BuildConfig.API_KEY, createPost).enqueue(callback)
    }

    override fun getPublication(callback: Callback<PublicationDTO>) {
        retrofitAirTable.getListPublication(BuildConfig.API_KEY).enqueue(callback)
    }
}