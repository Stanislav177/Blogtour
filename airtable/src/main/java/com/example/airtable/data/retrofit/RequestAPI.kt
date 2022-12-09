package com.example.airtable.data.retrofit

import com.example.airtable.data.DTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

const val URL_API_END_POINT = "v0/apppipNa4ediQvkED/Table%201/?"
const val API_KEY = "api_key"

interface RequestAPI {

    @GET(URL_API_END_POINT)
    fun getPosting(
        @Query(API_KEY) apikey: String,
    ): Call<DTO>
}