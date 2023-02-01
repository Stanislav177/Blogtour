package com.myblogtour.airtable.domain.retrofit

import com.google.gson.JsonObject
import com.myblogtour.airtable.domain.DTO
import com.myblogtour.airtable.domain.PublicationDTO
import com.myblogtour.airtable.domain.Records
import retrofit2.Call
import retrofit2.http.*

const val URL_API_END_POINT = "v0/apppipNa4ediQvkED/Table%201/?"
const val URL_API_END_POINT_TRAVEL_USER = "v0/appQW6UhhbjRHa0vs/UserProfile/?"
const val URL_API_END_POINT_TRAVEL_PUBLICATION = "v0/appQW6UhhbjRHa0vs/Publication/?"
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
    ): Call<Records>

    @GET(URL_API_END_POINT_TRAVEL_PUBLICATION)
    fun getListPublication(
        @Query(API_KEY) apikey: String,
    ) : Call<PublicationDTO>


}
