package com.myblogtour.blogtour.domain.repository

import com.myblogtour.airtable.domain.PublicationDTO

interface MyPublicationRepository {
    fun getMyPublication(
        uidUserProfile: String,
        onSuccess: (PublicationDTO) -> Unit,
        onError: ((Throwable)) -> Unit,
    )

    fun deletePublication(
        idPublication: String,
        onSuccess: (Boolean) -> Unit,
        onError: (Throwable) -> Unit
    )

    fun deleteLikePublication(idLike: String)
}