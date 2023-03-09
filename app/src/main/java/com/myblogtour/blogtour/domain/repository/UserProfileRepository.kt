package com.myblogtour.blogtour.domain.repository

import com.myblogtour.blogtour.domain.UserProfileEntity

interface UserProfileRepository {

    fun getUserProfile(
        id: String,
        onSuccess: (UserProfileEntity) -> Unit,
        onError: ((Throwable) -> Unit)
    )
}