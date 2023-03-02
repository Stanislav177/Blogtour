package com.myblogtour.blogtour.appState

sealed class AppStateResetPassword {
    object ResetPasswordState : AppStateResetPassword()
    data class NoResetPasswordState(val errorResetPassword: String) : AppStateResetPassword()
}
