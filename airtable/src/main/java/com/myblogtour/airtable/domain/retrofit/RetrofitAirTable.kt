package com.myblogtour.airtable.domain.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


const val URL_API_BASE = "https://api.airtable.com/"

interface RetrofitAirTable {

    companion object {
        fun startRetrofit(): RequestAPI {
            val retrofit = Retrofit.Builder().baseUrl(URL_API_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(RequestAPI::class.java)
        }
    }

}