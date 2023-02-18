package com.myblogtour.airtable.domain.retrofit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


const val URL_API_BASE = "https://api.airtable.com/"

interface RetrofitAirTable {

    companion object {
//        var interceptor: HttpLoggingInterceptor =
//            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
//
//        private val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        fun startRetrofit(): RequestAPI {
            val retrofit = Retrofit.Builder().baseUrl(URL_API_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(RequestAPI::class.java)
        }
    }

}