package com.myblogtour.blogtour.domain.repository

import com.google.gson.JsonObject
import com.myblogtour.airtable.domain.PublicationDTO
import com.myblogtour.airtable.domain.RecordUserProfileDTO
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface PublicationRepository {
    fun getPublication() : Single<PublicationDTO>

    fun updateComplaintPublication(
        idPublication: String,
        complaint: JsonObject
    )

    fun clickCounterLikePublication(
        idUser: String,
        like: JsonObject
    )

    fun loadingUserProfile(
        idUser: String?,
        onSuccess: (RecordUserProfileDTO) -> Unit,
        onError: ((Throwable)) -> Unit
    )
}