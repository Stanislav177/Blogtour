package com.example.airtable.data.repository

import com.example.airtable.data.DTO
import retrofit2.Callback

interface RepoAirTablePosting {

    fun getPostingAirTable(callback: Callback<DTO>)
}