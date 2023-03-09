package com.myblogtour.blogtour.ui.profileUser.resetPassword

import android.text.Editable
import androidx.lifecycle.LiveData

interface ResetPasswordContract {
    val reAuthentication: LiveData<Boolean>
    val errorReAuthentication: LiveData<String>
    val patternPassword: LiveData<String>
    val patternPasswordConfirm: LiveData<String>
    val updatePasswordUser: LiveData<Boolean>
    fun reAuth(email: String, password: String)
    fun newPassword(p1: Editable?, p2: Editable?)
}