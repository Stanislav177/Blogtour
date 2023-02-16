package com.myblogtour.blogtour.appState

import android.net.Uri
import com.google.firebase.auth.FirebaseUser

sealed class AppStateUserRegistration {
    data class ErrorEmail(val errorEmail: String) : AppStateUserRegistration()
    data class SuccessUser(val user: FirebaseUser?) : AppStateUserRegistration()
    data class ErrorUser(val userError: String) : AppStateUserRegistration()
    data class ErrorPassword(val errorPassword: String) : AppStateUserRegistration()
    data class ErrorPasswordEquals(val errorPasswordEquals: String) : AppStateUserRegistration()
    data class ErrorUserLogin(val errorUserName: String) : AppStateUserRegistration()
    data class ProgressLoadingIconUser(val progress: Int) : AppStateUserRegistration()
    data class SuccessIconUser(val successIcon: Boolean) : AppStateUserRegistration()
    data class DeleteIconUser(val deleteIcon: Boolean) : AppStateUserRegistration()
}