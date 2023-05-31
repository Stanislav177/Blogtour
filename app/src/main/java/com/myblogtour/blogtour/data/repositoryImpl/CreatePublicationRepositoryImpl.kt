package com.myblogtour.blogtour.data.repositoryImpl

import com.google.gson.JsonObject
import com.myblogtour.airtable.BuildConfig
import com.myblogtour.blogtour.data.retrofit.AirTableApi
import com.myblogtour.blogtour.domain.repository.CreatePublicationRepository
import io.reactivex.rxjava3.kotlin.subscribeBy

class CreatePublicationRepositoryImpl(private val api: AirTableApi) : CreatePublicationRepository {
    override fun createPublication(
        onSuccess: (Boolean) -> Unit,
        onError: (Throwable) -> Unit,
        publication: JsonObject,
    ) {
        api.createPublication(BuildConfig.API_KEY, publication).subscribeBy(
            onComplete = {
                onSuccess.invoke(true)
            }, onError = {
                onError.invoke(it)
            })
    }
}