package com.myblogtour.blogtour.data

import com.google.gson.JsonObject
import com.myblogtour.airtable.BuildConfig
import com.myblogtour.airtable.domain.RecordUserProfileDTO
import com.myblogtour.blogtour.data.retrofit.AirTableApi
import com.myblogtour.blogtour.domain.repository.UserRegistrationRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRegistrationRepositoryImpl(private val api: AirTableApi) : UserRegistrationRepository {
    override fun createUser(
        userJson: JsonObject,
        onSuccess: (RecordUserProfileDTO) -> Unit,
        onError: (Throwable) -> Unit,
    ) {
        api.createUserProfile(BuildConfig.API_KEY, userJson).enqueue(
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
                        onError.invoke(IllegalStateException("Что-то пошло не так"))
                    }
                }

                override fun onFailure(call: Call<RecordUserProfileDTO>, t: Throwable) {
                    onError.invoke(t)
                }

            }
        )
    }
}