package com.myblogtour.blogtour.domain.repository

import com.google.gson.JsonObject
import com.myblogtour.airtable.domain.RecordUserProfileDTO

interface UserRegistrationRepository {
    fun createUser(
        userJson: JsonObject,
        onSuccess: (RecordUserProfileDTO) -> Unit,
        onError: (Throwable) -> Unit,
    )
}