package com.myblogtour.blogtour.ui.authUser

import android.text.Editable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.myblogtour.blogtour.appState.AppStateUserAuth
import com.myblogtour.blogtour.domain.repository.AuthFirebaseRepository
import com.myblogtour.blogtour.utils.validatorEmail.EmailValidatorPatternImpl

class AuthUserViewModel(
    private val emailValidatorPattern: EmailValidatorPatternImpl = EmailValidatorPatternImpl(),
    private val liveData: MutableLiveData<AppStateUserAuth> = MutableLiveData(),
    private val authFirebaseRepository: AuthFirebaseRepository
) : ViewModel() {

    private lateinit var emailAuth: String
    private lateinit var passwordAuth: String

    fun getLiveData() = liveData

    fun authUser(
        email: Editable?,
        password: Editable?,
    ) {
        validEmail(email, password.toString())
    }

    fun sendEmailVerification() {
        authFirebaseRepository.verificationEmail {
            if (it) {
                singOut()
                liveData.postValue(AppStateUserAuth.SendVerification("Письмо отправлено"))
            } else {
                singOut()
                liveData.postValue(AppStateUserAuth.SendVerification("Что-то пошло не так"))
            }
        }
    }

    fun singOut() {
        authFirebaseRepository.singInOut {

        }
    }

    private fun validEmail(email: Editable?, password: String) {
        emailValidatorPattern.afterText(email)
        when {
            emailValidatorPattern.validEmail -> {
                emailAuth = email.toString()
                isNullPassword(password)
            }
            emailValidatorPattern.textEmail == null -> {
                liveData.postValue(AppStateUserAuth.ErrorValidEmail("Введите емайл"))
            }
            else -> {
                liveData.postValue(AppStateUserAuth.ErrorValidEmail("Некорректный емайл"))
            }
        }
    }

    private fun isNullPassword(password: String) {
        if (password == "") {
            liveData.postValue(AppStateUserAuth.ErrorNullPassword("Введите пароль"))
        } else {
            passwordAuth = password
            singInUser()
        }
    }

    private fun singInUser() {
        authFirebaseRepository.authUser(emailAuth, passwordAuth,
            onSuccess = {
                isEmailVerified()
            },
            onError = {
                liveData.postValue(AppStateUserAuth.ErrorUser(it))
            }
        )
    }

    private fun isEmailVerified() {
        authFirebaseRepository.isEmailVerified {
            if (it) {
                liveData.postValue(AppStateUserAuth.SuccessUser(it))
            } else {
                liveData.postValue(AppStateUserAuth.SuccessUserNoVerified(it))
            }
        }
    }
}