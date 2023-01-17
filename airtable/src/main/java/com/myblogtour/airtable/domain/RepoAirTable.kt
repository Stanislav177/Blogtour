package com.myblogtour.airtable.domain

import com.google.gson.JsonObject
import retrofit2.Callback

interface RepoAirTable {

    fun getPostingAirTable(callback: Callback<DTO>)

    fun createPostAirTable(createPost: JsonObject, callback: Callback<Record>)
}