package com.myblogtour.blogtour.utils.validatorPassword

import android.text.Editable

interface PasswordValidatorPattern {
    var validPassword: Boolean
    var equalsPassword: Boolean
    fun afterText(p0: Editable?, p0Confirm: Editable?)
}