package com.myblogtour.blogtour.domain.repository

import com.google.gson.JsonObject
import com.myblogtour.blogtour.domain.UserProfileEntity
import org.json.JSONObject

interface UserProfileRepository {
    fun getUserProfile(
        id: String,
        onSuccess: (UserProfileEntity) -> Unit,
        onError: ((Throwable) -> Unit)
    )
    fun saveUserProfile(
        id: String,
        jsonSave: JsonObject,
        onSuccess: (UserProfileEntity) -> Unit,
        onError: ((Throwable) -> Unit)
    )
}