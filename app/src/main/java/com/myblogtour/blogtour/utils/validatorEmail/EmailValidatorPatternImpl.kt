package com.myblogtour.blogtour.utils.validatorEmail

import android.text.Editable
import java.util.regex.Pattern

class EmailValidatorPatternImpl : EmailValidatorPattern {
    var textEmail: String? = null
    var validEmail = false

    override fun isNullEmail(email: String?): String? {
        return isNullEmailPattern(email)
    }

    override fun isValidEmail(email: CharSequence?): Boolean {
        return isValidEmailPattern(email)
    }

    override fun afterText(email: Editable?) {
        textEmail = isNullEmail(email.toString())
        validEmail = isValidEmail(email)
    }

    companion object {
        private val PATTERN_EMAIL = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")"
        )

        fun isNullEmailPattern(email: String?): String? {
            if (email.equals("")) {
                return null
            }
            return email
        }

        fun isValidEmailPattern(email: CharSequence?): Boolean {
            return email != null && PATTERN_EMAIL.matcher(email).matches()
        }
    }
}