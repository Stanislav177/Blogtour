package com.myblogtour.blogtour.domain.repository

import com.google.gson.JsonObject
import com.myblogtour.airtable.domain.Record

interface CreatePublicationRepository {
    fun createPublication(
        onSuccess: (Boolean) -> Unit,
        onError: (Throwable) -> Unit,
        publication: JsonObject
    )
}