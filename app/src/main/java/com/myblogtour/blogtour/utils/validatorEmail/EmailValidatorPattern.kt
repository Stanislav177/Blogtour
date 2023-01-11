package com.myblogtour.blogtour.utils.validatorEmail

import android.text.Editable

interface EmailValidatorPattern {
    fun isNullEmail(email: String?): String?
    fun isValidEmail(email: CharSequence?): Boolean
    fun afterText(email: Editable?)
}