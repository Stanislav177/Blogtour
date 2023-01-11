package com.myblogtour.blogtour.ui.registrationUser

import android.text.Editable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
import com.myblogtour.blogtour.appState.AppStateUserRegistration
import com.myblogtour.blogtour.utils.validatorEmail.EmailValidatorPatternImpl
import com.myblogtour.blogtour.utils.validatorPassword.PasswordValidatorPatternImpl
import com.myblogtour.blogtour.utils.validatorUserName.LoginValidatorPatternImpl

class RegistrationViewModel(
    private val liveData: MutableLiveData<AppStateUserRegistration> = MutableLiveData(),
    private val validEmailPattern: EmailValidatorPatternImpl = EmailValidatorPatternImpl(),
    private val validPasswordPattern: PasswordValidatorPatternImpl = PasswordValidatorPatternImpl(),
    private val validNameValidatorPattern: LoginValidatorPatternImpl = LoginValidatorPatternImpl(),
) : ViewModel() {

    private lateinit var userLogin: String
    private lateinit var email: String
    private lateinit var password: String

    private val auth by lazy { Firebase.auth }

    fun getLiveData() = liveData

    fun registerUserFb(
        loginUserRegister: Editable?,
        emailRegister: Editable?,
        passwordOneRegister: Editable?,
        passwordTwoRegister: Editable?,
    ) {
        validNameValidatorPattern.afterTextUserName(loginUserRegister)
        when {
            validNameValidatorPattern.validUserLogin -> {
                userLogin = loginUserRegister.toString()
                emailValidator(emailRegister, passwordOneRegister, passwordTwoRegister)
            }
            validNameValidatorPattern.nullUserLogin == null -> {
                liveData.postValue(AppStateUserRegistration.ErrorUserLogin("Введите логин"))
            }
            else -> {
                liveData.postValue(AppStateUserRegistration.ErrorUserLogin("Некорректный логин"))
            }
        }
    }

    private fun createAccount() {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                val user = auth.currentUser
                val profileUser = userProfileChangeRequest {
                    displayName = userLogin
                }
                user!!.updateProfile(profileUser)
                liveData.postValue(AppStateUserRegistration.SuccessUser(user))
            } else {
                liveData.postValue(AppStateUserRegistration.ErrorUser("Попробуйте позже"))
            }
        }
    }

    private fun passwordValidator(
        passwordOneRegister: Editable?,
        passwordTwoRegister: Editable?,
    ) {
        validPasswordPattern.afterText(passwordOneRegister, passwordTwoRegister)
        when {
            validPasswordPattern.validPassword -> {
                if (validPasswordPattern.equalsPassword) {
                    password = passwordTwoRegister.toString()
                    createAccount()
                } else {
                    liveData.postValue(AppStateUserRegistration.ErrorPasswordEquals("Пароли не совпадают"))
                }
            }
            else -> {
                liveData.postValue(AppStateUserRegistration.ErrorPassword("Легкий пароль"))
            }
        }
    }

    private fun emailValidator(
        emailRegister: Editable?,
        passwordOneRegister: Editable?,
        passwordTwoRegister: Editable?,
    ) {
        validEmailPattern.afterText(emailRegister)
        when {
            validEmailPattern.validEmail -> {
                email = emailRegister.toString()
                passwordValidator(passwordOneRegister, passwordTwoRegister)
            }
            validEmailPattern.textEmail == null -> {
                liveData.postValue(AppStateUserRegistration.ErrorEmail("Введите email"))
            }
            else -> {
                liveData.postValue(AppStateUserRegistration.ErrorEmail("Email введен некорректно"))
            }
        }
    }
}