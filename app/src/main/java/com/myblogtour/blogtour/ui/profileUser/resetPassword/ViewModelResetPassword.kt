package com.myblogtour.blogtour.ui.profileUser.resetPassword

import android.text.Editable
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.myblogtour.blogtour.utils.SingleLiveEvent
import com.myblogtour.blogtour.utils.validatorPassword.PasswordValidatorPattern

class ViewModelResetPassword(
    private val auth: FirebaseAuth,
    private val validPasswordPattern: PasswordValidatorPattern
) : ViewModel(), ResetPasswordContract {
    override val reAuthentication: LiveData<Boolean> = SingleLiveEvent()
    override val errorReAuthentication: LiveData<String> = SingleLiveEvent()
    override val patternPassword: LiveData<String> = SingleLiveEvent()
    override val patternPasswordConfirm: LiveData<String> = SingleLiveEvent()
    override val updatePasswordUser: LiveData<Boolean> = SingleLiveEvent()

    override fun reAuth(email: String, password: String) {
        if (password.isNotEmpty()) {
            password.let { pass ->
                val credential = EmailAuthProvider.getCredential(email, pass)
                auth.currentUser?.let {
                    it.reauthenticate(credential).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            reAuthentication.mutable().postValue(true)
                        } else {
                            reAuthentication.mutable().postValue(false)
                        }
                    }
                }
            }
        } else {
            errorReAuthentication.mutable().postValue("Введите пароль")
        }
    }

    override fun newPassword(p1: Editable?, p2: Editable?) {
        validPasswordPattern.afterText(p1, p2)
        when {
            validPasswordPattern.validPassword -> {
                if (validPasswordPattern.equalsPassword) {
                    Log.d("TEST", "ВСЕ ОК")
                    upDatePassword(p2)
                } else {
                    patternPasswordConfirm.mutable().postValue("Пароли не совпадают")
                }
            }
            else -> {
                patternPassword.mutable().postValue("Легкий пароль")
            }
        }
    }

    private fun upDatePassword(password: Editable?) {
        auth.currentUser?.let {
            it.updatePassword(password.toString()).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    updatePasswordUser.mutable().postValue(true)
                } else {
                    updatePasswordUser.mutable().postValue(false)
                }
            }
        }
    }

    private fun <T> LiveData<T>.mutable(): MutableLiveData<T> {
        return this as? MutableLiveData<T> ?: throw IllegalStateException("O_o")
    }
}