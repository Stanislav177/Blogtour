package com.myblogtour.blogtour.ui.registrationUser

import android.text.Editable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.myblogtour.blogtour.appState.AppStateUserRegistration
import com.myblogtour.blogtour.utils.validatorEmail.EmailValidatorPatternImpl
import com.myblogtour.blogtour.utils.validatorPassword.PasswordValidatorPatternImpl

class RegistrationViewModel(
    private val liveData: MutableLiveData<AppStateUserRegistration> = MutableLiveData(),
    private val validEmailPattern: EmailValidatorPatternImpl = EmailValidatorPatternImpl(),
    private val validPasswordPattern: PasswordValidatorPatternImpl = PasswordValidatorPatternImpl(),
) : ViewModel() {

    private var nameUser = "User"
    private var email: String? = null
    private var password: String? = null
    private var passwordConfirm: String? = null
    private var flagCorrectEmail = false

    fun getLiveData() = liveData

    fun registerUser() {

    }

    private fun setPasswordValidator(passwordOne: Editable? = null, passwordTwo: Editable? = null) {
        validPasswordPattern.afterText(passwordOne, passwordTwo)
        when {
            validPasswordPattern.validPassword -> {
                if (validPasswordPattern.equalsPassword) {
                    liveData.postValue(AppStateUserRegistration.SuccessPassword(true))
                } else {
                    liveData.postValue(AppStateUserRegistration.ErrorPassword("Пароли не совпадают"))
                }
            }
            else -> {
                liveData.postValue(AppStateUserRegistration.ErrorPassword("Легкий пароль"))
            }
        }

    }

    fun setEmailValidator(
        emailStr: Editable? = null,
        passwordOne: Editable?,
        passwordTwo: Editable?,
    ) {
        validEmailPattern.afterText(emailStr)

        when {
            validEmailPattern.validEmail -> {
                email = emailStr.toString()
                setPasswordValidator(passwordOne, passwordTwo)
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