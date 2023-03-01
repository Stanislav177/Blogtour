package com.myblogtour.blogtour.domain.repository

import com.google.firebase.auth.FirebaseUser

interface AuthFirebaseRepository {
    fun userCurrent(onSuccess: (FirebaseUser) -> Unit, onError: (String) -> Unit)
    fun authUser(
        emailAuth: String,
        passwordAuth: String,
        onSuccess: (Boolean) -> Unit,
        onError: (String) -> Unit
    )

    fun registrationUser(
        email: String,
        password: String,
        onSuccess: (String) -> Unit,
        onError: (String) -> Unit
    )

    fun updateUser(
        idAirtable: String,
        icon: String,
        onSuccess: (Boolean) -> Unit,
    )

    fun singInOut(
        onSuccess: (Boolean) -> Unit
    )

    fun deleteAccountUser()
}