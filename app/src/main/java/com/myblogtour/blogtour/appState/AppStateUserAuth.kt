package com.myblogtour.blogtour.appState

sealed class AppStateUserAuth {
    data class ErrorValidEmail(val errorValidEmail: String) : AppStateUserAuth()
    data class ErrorNullPassword(val errorNullPassword: String) : AppStateUserAuth()
    data class SuccessUser(val successUser: Boolean) : AppStateUserAuth()
    data class ErrorUser(val errorUser: String) : AppStateUserAuth()
}
