package com.myblogtour.blogtour.data.repositoryImpl

import com.google.gson.JsonObject
import com.myblogtour.airtable.BuildConfig
import com.myblogtour.airtable.domain.PublicationDTO
import com.myblogtour.airtable.domain.RecordUserProfileDTO
import com.myblogtour.blogtour.data.retrofit.AirTableApi
import com.myblogtour.blogtour.domain.repository.PublicationRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PublicationRepositoryImpl(private val api: AirTableApi) : PublicationRepository {

    override fun getPublication(
        onSuccess: (PublicationDTO) -> Unit,
        onError: ((Throwable)) -> Unit
    ) {
        api.getPublication(BuildConfig.API_KEY).enqueue(
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
                        onError.invoke(IllegalStateException("Нет данных"))
                    }
                }

                override fun onFailure(call: Call<PublicationDTO>, t: Throwable) {
                    onError.invoke(t)
                }
            }
        )
    }

    override fun updateComplaintPublication(
        idPublication: String,
        complaint: JsonObject,
    ) {
        Thread {
            api.updatePublicationComplaint(idPublication, BuildConfig.API_KEY, complaint).execute()
        }.start()
    }

    override fun clickCounterLikePublication(idUser: String, like: JsonObject) {
        Thread {
            api.updateUserProfileCounterLike(idUser, BuildConfig.API_KEY, like).execute()
        }.start()
    }

    override fun loadingUserProfile(
        idUser: String?,
        onSuccess: (RecordUserProfileDTO) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        idUser?.let { idNotNull ->
            api.getProfileUser(idNotNull, BuildConfig.API_KEY).enqueue(
                object : Callback<RecordUserProfileDTO> {
                    override fun onResponse(
                        call: Call<RecordUserProfileDTO>,
                        response: Response<RecordUserProfileDTO>
                    ) {
                        if (response.isSuccessful) {
                            response.body()?.let {
                                onSuccess.invoke(it)
                            }
                        } else {
                            onError.invoke(IllegalStateException("Данных нет"))
                        }
                    }

                    override fun onFailure(call: Call<RecordUserProfileDTO>, t: Throwable) {
                        onError.invoke(t)
                    }
                }
            )
        }
    }
}
