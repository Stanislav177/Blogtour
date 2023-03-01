package com.myblogtour.blogtour.domain.repository

import com.google.gson.JsonObject
import com.myblogtour.airtable.domain.UserProfileDTO
import com.myblogtour.blogtour.domain.UserProfileEntity

interface UserProfileRepository {
    fun createUserProfile(
        createUserJson: JsonObject,
        onSuccess: (UserProfileDTO) -> Unit,
        onError: ((Throwable) -> Unit)
    )

    fun getUserProfile(
        id: String,
        onSuccess: (UserProfileEntity) -> Unit,
        onError: ((Throwable) -> Unit)
    )
}