package com.myblogtour.blogtour.data

import com.google.gson.JsonObject
import com.myblogtour.airtable.BuildConfig
import com.myblogtour.airtable.domain.RecordUserProfileDTO
import com.myblogtour.airtable.domain.UserProfileDTO
import com.myblogtour.blogtour.data.retrofit.AirTableApi
import com.myblogtour.blogtour.domain.UserProfileEntity
import com.myblogtour.blogtour.domain.repository.UserProfileRepository
import com.myblogtour.blogtour.utils.converterFromRegisterUserAirtableToUserEntity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserProfileRepositoryImpl(private val api: AirTableApi) : UserProfileRepository {
    override fun createUserProfile(
        createUserJson: JsonObject,
        onSuccess: (UserProfileDTO) -> Unit,
        onError: (Throwable) -> Unit
    ) {

    }

    override fun getUserProfile(
        id: String,
        onSuccess: (UserProfileEntity) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        api.getProfileUser(id, BuildConfig.API_KEY).enqueue(
            object : Callback<RecordUserProfileDTO> {
                override fun onResponse(
                    call: Call<RecordUserProfileDTO>,
                    response: Response<RecordUserProfileDTO>
                ) {
                    val body = response.body()
                    if (response.isSuccessful && body != null) {
                        onSuccess.invoke(converterFromRegisterUserAirtableToUserEntity(body))
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