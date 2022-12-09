package com.example.airtable.domain

import com.example.airtable.BuildConfig
import com.example.airtable.data.DTO
import com.example.airtable.data.repository.RepoAirTablePosting
import com.example.airtable.data.retrofit.RetrofitAirTable
import retrofit2.Callback

class RepoAirTablePostingImpl : RepoAirTablePosting {

    private val retrofitAirTable by lazy { RetrofitAirTable.startRetrofit() }

    override fun getPostingAirTable(callback: Callback<DTO>) {
        retrofitAirTable.getPosting(BuildConfig.API_KEY).enqueue(callback)
    }
}