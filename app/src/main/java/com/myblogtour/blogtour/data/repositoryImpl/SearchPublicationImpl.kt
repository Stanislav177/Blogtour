package com.myblogtour.blogtour.data.repositoryImpl

import com.myblogtour.airtable.BuildConfig
import com.myblogtour.airtable.domain.PublicationDTO
import com.myblogtour.blogtour.data.retrofit.AirTableApi
import com.myblogtour.blogtour.domain.repository.SearchPublication
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchPublicationImpl(private val airTableApi: AirTableApi) : SearchPublication {
    override fun getSearchPublication(
        searchText: String,
        onSuccess: (PublicationDTO) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        airTableApi.getSearchPublication(BuildConfig.API_KEY, searchText).enqueue(
            object : Callback<PublicationDTO> {
                override fun onResponse(
                    call: Call<PublicationDTO>,
                    response: Response<PublicationDTO>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            onSuccess.invoke(it)
                        }
                    } else {
                        onError.invoke(IllegalStateException("Данных нет"))
                    }
                }

                override fun onFailure(call: Call<PublicationDTO>, t: Throwable) {
                    onError.invoke(IllegalStateException(t.message))
                }
            }
        )
    }
}