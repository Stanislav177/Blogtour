package com.myblogtour.blogtour.utils.validatorEmail

import android.text.Editable

interface EmailValidatorPattern {
    var textEmail: String?
    var validEmail: Boolean
    fun isNullEmail(email: String?): String?
    fun isValidEmail(email: CharSequence?): Boolean
    fun afterText(email: Editable?)
}