package com.myblogtour.airtable.domain.retrofit

import com.google.gson.JsonObject
import com.myblogtour.airtable.domain.PublicationDTO
import com.myblogtour.airtable.domain.Record
import com.myblogtour.airtable.domain.RecordUserProfileDTO
import retrofit2.Call
import retrofit2.http.*

const val URL_API_END_POINT = "v0/apppipNa4ediQvkED/Table%201/?"
const val URL_API_END_POINT_TRAVEL_USER = "v0/appQW6UhhbjRHa0vs/UserProfile/?"
const val URL_API_END_POINT_TRAVEL_PUBLICATION = "v0/appQW6UhhbjRHa0vs/Publication/?"
const val API_KEY = "api_key"

interface RequestAPI {

    @Headers("Content-Type: application/json")
    @POST(URL_API_END_POINT)
    fun createPublication(
        @Query(API_KEY) apikey: String,
        @Body post: JsonObject,
    ): Call<Record>

    @GET(URL_API_END_POINT_TRAVEL_PUBLICATION)
    fun getListPublication(
        @Query(API_KEY) apikey: String,
    ): Call<PublicationDTO>

    @Headers("Content-Type: application/json")
    @POST(URL_API_END_POINT_TRAVEL_USER)
    fun createUserProfile(
        @Query(API_KEY) apikey: String,
        @Body post: JsonObject,
    ): Call<RecordUserProfileDTO>
}
