package com.myblogtour.blogtour.data.repositoryImpl

import com.google.gson.JsonObject
import com.myblogtour.airtable.BuildConfig
import com.myblogtour.airtable.domain.RecordUserProfileDTO
import com.myblogtour.blogtour.data.retrofit.AirTableApi
import com.myblogtour.blogtour.domain.repository.PublicationRepository
import io.reactivex.rxjava3.kotlin.subscribeBy

class PublicationRepositoryImpl(private val api: AirTableApi) : PublicationRepository {

    override fun getPublication() = api.getPublication(BuildConfig.API_KEY)

    override fun updateComplaintPublication(
        idPublication: String,
        complaint: JsonObject,
    ) {
        api.updatePublicationComplaint(idPublication, BuildConfig.API_KEY, complaint).subscribe()
    }


    override fun clickCounterLikePublication(idUser: String, like: JsonObject) {
        api.updateUserProfileCounterLike(idUser, BuildConfig.API_KEY, like).subscribe()
    }

    override fun loadingUserProfile(
        idUser: String?,
        onSuccess: (RecordUserProfileDTO) -> Unit,
        onError: (Throwable) -> Unit,
    ) {
        idUser?.let { idNotNull ->
            api.getProfileUser(idNotNull, BuildConfig.API_KEY).subscribeBy(
                onSuccess = {
                    onSuccess.invoke(it)
                },
                onError = {
                    onError.invoke(it)
                }
            )
        }
    }
}
