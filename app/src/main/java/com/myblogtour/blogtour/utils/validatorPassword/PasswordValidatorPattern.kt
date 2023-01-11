package com.myblogtour.blogtour.utils.validatorPassword

import android.text.Editable

interface PasswordValidatorPattern {
    fun afterText(p0: Editable?, p0Confirm: Editable?)
}