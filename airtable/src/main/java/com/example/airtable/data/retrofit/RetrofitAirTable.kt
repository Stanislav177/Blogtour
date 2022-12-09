package com.example.airtable.data.retrofit

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val URL_API_BASE = "https://api.airtable.com/"

interface RetrofitAirTable {

    companion object {
        fun startRetrofit(): RequestAPI {
            val retrofit = Retrofit.Builder().baseUrl(URL_API_BASE)
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient()
                    .create()))
                .build()
            return retrofit.create(RequestAPI::class.java)
        }
    }
}