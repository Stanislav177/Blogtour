package com.myblogtour.blogtour.ui.recoveryPassword

import androidx.lifecycle.ViewModel
import com.myblogtour.blogtour.domain.repository.AuthFirebaseRepository

class RecoveryPasswordViewModel(
    private val authFirebaseRepository: AuthFirebaseRepository
) : ViewModel() {

    fun resetPassword(emailReset: String) {
        authFirebaseRepository.resetPassword(emailReset,
            onSuccess = {
                val test = it
            },
            onError = {
                val error = it
            }
        )
    }
}