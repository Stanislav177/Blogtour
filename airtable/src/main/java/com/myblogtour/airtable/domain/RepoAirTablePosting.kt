package com.myblogtour.airtable.domain

import com.google.gson.JsonObject
import okhttp3.RequestBody
import retrofit2.Callback

interface RepoAirTablePosting {

    fun getPostingAirTable(callback: Callback<DTO>)

    fun createPostAirTable(createPost: JsonObject, callback: Callback<Record>)
}