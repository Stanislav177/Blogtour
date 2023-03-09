package com.myblogtour.blogtour.utils.validatorUserName

import android.text.Editable

interface LoginValidatorPattern {
    var validUserLogin: Boolean
    var nullUserLogin: String?
    fun afterTextUserName(userLoginText: Editable?)
}