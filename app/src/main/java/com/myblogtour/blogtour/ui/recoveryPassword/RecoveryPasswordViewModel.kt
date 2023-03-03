package com.myblogtour.blogtour.ui.recoveryPassword

import android.text.Editable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.myblogtour.blogtour.appState.AppStateResetPassword
import com.myblogtour.blogtour.domain.repository.AuthFirebaseRepository
import com.myblogtour.blogtour.utils.validatorEmail.EmailValidatorPatternImpl

class RecoveryPasswordViewModel(
    private val authFirebaseRepository: AuthFirebaseRepository,
    private val emailValidatorPattern: EmailValidatorPatternImpl = EmailValidatorPatternImpl(),
    private val liveData: MutableLiveData<AppStateResetPassword> = MutableLiveData()
) : ViewModel() {

    fun getLiveData() = liveData

    fun resetPassword(emailReset: Editable?) {
        emailValidatorPattern.afterText(emailReset)
        when {
            emailValidatorPattern.validEmail -> {
                authFirebaseRepository.resetPassword(
                    emailReset.toString()
                ) {
                    if (it) {
                        liveData.postValue(AppStateResetPassword.ResetPasswordState)
                    } else {
                        liveData.postValue(AppStateResetPassword.NoResetPasswordState("Пользователя с таким Email не существует"))
                    }
                }
            }
            emailValidatorPattern.textEmail == null -> {
                liveData.postValue(AppStateResetPassword.NoResetPasswordState("Введите адрес электронной почты"))
            }
            else -> {
                liveData.postValue(AppStateResetPassword.NoResetPasswordState("Введен некорректный адрес электронной почты"))
            }
        }
    }
}