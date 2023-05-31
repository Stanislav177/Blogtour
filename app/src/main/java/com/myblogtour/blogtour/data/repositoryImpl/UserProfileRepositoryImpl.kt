package com.myblogtour.blogtour.data.repositoryImpl

import com.google.firebase.auth.FirebaseAuth
import com.google.gson.JsonObject
import com.myblogtour.airtable.BuildConfig
import com.myblogtour.blogtour.data.retrofit.AirTableApi
import com.myblogtour.blogtour.domain.UserProfileEntity
import com.myblogtour.blogtour.domain.repository.UserProfileRepository
import com.myblogtour.blogtour.utils.converterFromProfileUserAirtableToUserEntity
import com.myblogtour.blogtour.utils.converterFromRegisterUserAirtableToUserEntity
import io.reactivex.rxjava3.kotlin.subscribeBy

class UserProfileRepositoryImpl(
    private val api: AirTableApi,
    private val userAuth: FirebaseAuth,
) : UserProfileRepository {

    override fun getUserProfile(
        id: String,
        onSuccess: (UserProfileEntity) -> Unit,
        onError: (Throwable) -> Unit,
    ) {
        api.getProfileUser(id, BuildConfig.API_KEY).subscribeBy(
            onSuccess = {
                onSuccess.invoke(
                    converterFromProfileUserAirtableToUserEntity(
                        getUserProfileVerification(),
                        getUserProfileMail(),
                        it
                    )
                )
            },
            onError = { onError.invoke(it) }
        )
    }

    override fun saveUserProfile(
        id: String,
        jsonSave: JsonObject,
        onSuccess: (UserProfileEntity) -> Unit,
        onError: (Throwable) -> Unit,
    ) {
        api.updateUserProfile(id, BuildConfig.API_KEY, jsonSave).subscribeBy(
            onSuccess = {
                onSuccess.invoke(converterFromRegisterUserAirtableToUserEntity(it))
            },
            onError = {
                onError.invoke(it)
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