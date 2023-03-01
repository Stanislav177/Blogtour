package com.myblogtour.blogtour.data

import com.myblogtour.airtable.BuildConfig
import com.myblogtour.airtable.domain.PublicationDTO
import com.myblogtour.blogtour.data.retrofit.AirTableApi
import com.myblogtour.blogtour.domain.repository.MyPublicationRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyPublicationRepositoryImpl(private val api: AirTableApi) : MyPublicationRepository {
    override fun getMyPublication(
        uidUserProfile: String,
        onSuccess: (PublicationDTO) -> Unit,
        onError: ((Throwable)) -> Unit,
    ) {
        api.getMyPublication(BuildConfig.API_KEY, uidUserProfile).enqueue(
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
                    onError.invoke(t)
                }
            }
        )
    }

    override fun deletePublication(
        idPublication: String,
        onSuccess: (Boolean) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        api.deletePublication(idPublication, BuildConfig.API_KEY).enqueue(
            object : Callback<Unit> {
                override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            onSuccess.invoke(true)
                        }
                    } else {
                        onError.invoke(IllegalStateException("Что-то пошло не так"))
                    }
                }

                override fun onFailure(call: Call<Unit>, t: Throwable) {
                    onError.invoke(t)
                }

            }
        )
    }

    override fun deleteLikePublication(idLike: String) {
        Thread {
            api.deleteLike(idLike, BuildConfig.API_KEY).execute()
        }.start()
    }
}