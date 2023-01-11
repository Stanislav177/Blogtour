package com.myblogtour.blogtour.ui.authUser

import android.text.Editable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.myblogtour.blogtour.appState.AppStateUserAuth
import com.myblogtour.blogtour.utils.validatorEmail.EmailValidatorPatternImpl

class AuthUserViewModel(
    private val emailValidatorPattern: EmailValidatorPatternImpl = EmailValidatorPatternImpl(),
    private val liveData: MutableLiveData<AppStateUserAuth> = MutableLiveData(),
) : ViewModel() {
    private lateinit var emailAuth: String
    private lateinit var passwordAuth: String

    private val auth by lazy { Firebase.auth }

    fun getLiveData() = liveData

    fun authUser(
        email: Editable?,
        password: Editable?,
    ) {
        validEmail(email, password.toString())
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
            else ->{
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
        auth.signInWithEmailAndPassword(emailAuth, passwordAuth).addOnCompleteListener {
            if (it.isSuccessful) {
                liveData.postValue(AppStateUserAuth.SuccessUser(true))
            } else {
                liveData.postValue(AppStateUserAuth.ErrorUser("Ошибка авторизации"))
            }
        }
    }

}