package com.myblogtour.blogtour.domain.repository

import com.google.gson.JsonObject
import com.myblogtour.airtable.domain.PublicationDTO
import com.myblogtour.airtable.domain.RecordUserProfileDTO
import io.reactivex.rxjava3.core.Completable

interface SearchPublication {
    fun getSearchPublication(
        searchText: String,
        onSuccess: (PublicationDTO) -> Unit,
        onError: ((Throwable)) -> Unit,
    )

    fun updateComplaintPublication(
        idPublication: String,
        complaint: JsonObject,
    ): Completable

    fun clickCounterLikePublication(
        idUser: String,
        like: JsonObject,
    )

    fun loadingUserProfile(
        idUser: String?,
        onSuccess: (RecordUserProfileDTO) -> Unit,
        onError: ((Throwable)) -> Unit,
    )
}