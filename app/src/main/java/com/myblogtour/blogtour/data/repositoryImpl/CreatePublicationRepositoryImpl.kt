package com.myblogtour.blogtour.data.repositoryImpl

import com.google.gson.JsonObject
import com.myblogtour.airtable.BuildConfig
import com.myblogtour.airtable.domain.Record
import com.myblogtour.blogtour.data.retrofit.AirTableApi
import com.myblogtour.blogtour.domain.repository.CreatePublicationRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreatePublicationRepositoryImpl(private val api: AirTableApi) : CreatePublicationRepository {
    override fun createPublication(
        onSuccess: (Boolean) -> Unit,
        onError: (Throwable) -> Unit,
        publication: JsonObject
    ) {
        api.createPublication(BuildConfig.API_KEY, publication).enqueue(
            object : Callback<Record> {
                override fun onResponse(call: Call<Record>, response: Response<Record>) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            onSuccess.invoke(true)
                        }
                    } else {
                        onError.invoke(IllegalStateException("Что-то пошло не так"))
                    }
                }

                override fun onFailure(call: Call<Record>, t: Throwable) {
                    onError.invoke(t)
                }
            }
        )
    }
}