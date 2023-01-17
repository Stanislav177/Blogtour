package com.myblogtour.airtable.domain.retrofit

import com.google.gson.JsonObject
import com.myblogtour.airtable.domain.DTO
import com.myblogtour.airtable.domain.Record
import retrofit2.Call
import retrofit2.http.*

const val URL_API_END_POINT = "v0/apppipNa4ediQvkED/Table%201/?"
const val API_KEY = "api_key"

interface RequestAPI {

    @GET(URL_API_END_POINT)
    fun getPosting(
        @Query(API_KEY) apikey: String,
    ): Call<DTO>

    @Headers("Content-Type: application/json")
    @POST(URL_API_END_POINT)
    fun createPost(
        @Query(API_KEY) apikey: String,
        @Body post: JsonObject,
    ): Call<Record>
}
