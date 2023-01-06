package com.myblogtour.blogtour.appState

sealed class AppStateUserRegistration {
    data class ErrorEmail(val errorEmail: String) : AppStateUserRegistration()
    data class SuccessEmail(val successEmail: Boolean) : AppStateUserRegistration()
    data class ErrorPassword(val errorPassword: String) : AppStateUserRegistration()
    data class SuccessPassword(val successPassword: Boolean) : AppStateUserRegistration()
}