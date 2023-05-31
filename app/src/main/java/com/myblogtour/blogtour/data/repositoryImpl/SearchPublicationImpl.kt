package com.myblogtour.blogtour.data.repositoryImpl

import com.google.gson.JsonObject
import com.myblogtour.airtable.BuildConfig
import com.myblogtour.airtable.domain.PublicationDTO
import com.myblogtour.airtable.domain.RecordUserProfileDTO
import com.myblogtour.blogtour.data.retrofit.AirTableApi
import com.myblogtour.blogtour.domain.repository.SearchPublication
import io.reactivex.rxjava3.kotlin.subscribeBy
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchPublicationImpl(private val airTableApi: AirTableApi) : SearchPublication {
    override fun getSearchPublication(
        searchText: String,
        onSuccess: (PublicationDTO) -> Unit,
        onError: (Throwable) -> Unit,
    ) {
        airTableApi.getSearchPublication(BuildConfig.API_KEY, searchText).subscribeBy(
            onSuccess = {
                onSuccess.invoke(it)
            },
            onError = {
                onError.invoke(it)
            }
        )
    }

    override fun updateComplaintPublication(idPublication: String, complaint: JsonObject) =
        airTableApi.updatePublicationComplaint(idPublication, BuildConfig.API_KEY, complaint)

    override fun clickCounterLikePublication(idUser: String, like: JsonObject) {
        Thread {
            airTableApi.updateUserProfileCounterLike(idUser, BuildConfig.API_KEY, like)
        }.start()
    }

    override fun loadingUserProfile(
        idUser: String?,
        onSuccess: (RecordUserProfileDTO) -> Unit,
        onError: (Throwable) -> Unit,
    ) {
        idUser?.let { idNotNull ->
            airTableApi.getProfileUser(idNotNull, BuildConfig.API_KEY).subscribeBy(
                onSuccess = { onSuccess.invoke(it) },
                onError = { onError.invoke(it) }
            )
        }
    }
}