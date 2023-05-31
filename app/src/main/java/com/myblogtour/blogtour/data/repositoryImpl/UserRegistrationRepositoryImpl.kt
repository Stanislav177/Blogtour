package com.myblogtour.blogtour.data.repositoryImpl

import com.google.gson.JsonObject
import com.myblogtour.airtable.BuildConfig
import com.myblogtour.airtable.domain.RecordUserProfileDTO
import com.myblogtour.blogtour.data.retrofit.AirTableApi
import com.myblogtour.blogtour.domain.repository.UserRegistrationRepository
import io.reactivex.rxjava3.kotlin.subscribeBy

class UserRegistrationRepositoryImpl(private val api: AirTableApi) : UserRegistrationRepository {
    override fun createUser(
        userJson: JsonObject,
        onSuccess: (RecordUserProfileDTO) -> Unit,
        onError: (Throwable) -> Unit,
    ) {
        api.createUserProfile(BuildConfig.API_KEY, userJson).subscribeBy(
            onSuccess = {
                onSuccess.invoke(it)
            },
            onError = {
                onError.invoke(it)
            }
        )
    }
}