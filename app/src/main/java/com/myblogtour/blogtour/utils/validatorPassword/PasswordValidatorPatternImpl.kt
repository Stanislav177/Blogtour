package com.myblogtour.blogtour.utils.validatorPassword

import android.text.Editable

class PasswordValidatorPatternImpl : PasswordValidatorPattern {

    var validPassword = false
    var equalsPassword = false

    override fun afterText(p0: Editable?, p0Confirm: Editable?) {
        validPassword = isPasswordValidate(p0.toString())
        equalsPassword = isEqualsPassword(p0.toString(), p0Confirm.toString())
    }

    companion object {

        fun isPasswordValidate(passOne: String): Boolean {
            when {
                passOne.length < 6 -> {
                    return false
                }
                !passOne.matches(".*[A-Z].*".toRegex()) -> {
                    return false
                }
                !passOne.matches(".*[a-z].*".toRegex()) -> {
                    return false
                }
                !passOne.matches(".*[!@#$%^&*+=/?].*".toRegex()) -> {
                    return false
                }
            }
            return true
        }

        fun isEqualsPassword(p0: String?, p0Confirm: String?): Boolean {
            if (p0.equals(p0Confirm)) {
                return true
            }
            return false
        }


    }
}