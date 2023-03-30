package com.myblogtour.blogtour.data.repositoryImpl

import com.google.firebase.auth.FirebaseAuth
import com.google.gson.JsonObject
import com.myblogtour.airtable.BuildConfig
import com.myblogtour.airtable.domain.RecordUserProfileDTO
import com.myblogtour.blogtour.data.retrofit.AirTableApi
import com.myblogtour.blogtour.domain.UserProfileEntity
import com.myblogtour.blogtour.domain.repository.UserProfileRepository
import com.myblogtour.blogtour.utils.converterFromProfileUserAirtableToUserEntity
import com.myblogtour.blogtour.utils.converterFromRegisterUserAirtableToUserEntity
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserProfileRepositoryImpl(
    private val api: AirTableApi,
    private val userAuth: FirebaseAuth
) : UserProfileRepository {

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
                        onSuccess.invoke(
                            converterFromProfileUserAirtableToUserEntity(
                                getUserProfileVerification(),
                                getUserProfileMail(),
                                body
                            )
                        )
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

    override fun saveUserProfile(
        id: String,
        jsonSave: JsonObject,
        onSuccess: (UserProfileEntity) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        api.updateUserProfile(id, BuildConfig.API_KEY, jsonSave).enqueue(
            object : Callback<RecordUserProfileDTO> {
                override fun onResponse(
                    call: Call<RecordUserProfileDTO>,
                    response: Response<RecordUserProfileDTO>
                ) {
                    val body = response.body()
                    if (response.isSuccessful && body != null) {
                        onSuccess.invoke(converterFromRegisterUserAirtableToUserEntity(body))
                    } else {
                        onError.invoke(IllegalStateException("Что-то пошло не так"))
                    }
                }

                override fun onFailure(call: Call<RecordUserProfileDTO>, t: Throwable) {
                    onError.invoke(IllegalStateException("Что-то пошло не так"))
                }

            }
        )
    }

    private fun getUserProfileVerification(): Boolean {
        var verification = false
        userAuth.currentUser?.let {
            verification = it.isEmailVerified
        }
//        userAuth.addAuthStateListener { auth->
//            auth.currentUser?.let {
//                verification = it.isEmailVerified
//            }
        return verification
    }

    private fun getUserProfileMail(): String {
        var email = ""
        userAuth.currentUser?.let {
            email = it.email!!
        }
        return email
    }
}