package com.myblogtour.blogtour.utils.validatorUserName

import android.text.Editable

interface LoginValidatorPattern {
    fun afterTextUserName(userLoginText: Editable?)
}