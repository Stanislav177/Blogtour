package com.myblogtour.blogtour.utils.validatorUserName

import android.text.Editable

class LoginValidatorPatternImpl : LoginValidatorPattern {
    override var validUserLogin: Boolean = false
    override var nullUserLogin: String? = null

    override fun afterTextUserName(userLoginText: Editable?) {
        validUserLogin = isUserLoginValidate(userLoginText.toString())
        nullUserLogin = isUserLoginNull(userLoginText.toString())
    }

    companion object {
        fun isUserLoginValidate(strLogin: String): Boolean {
            when {
                strLogin.length < 5 -> {
                    return false
                }
//                strLogin.matches(".*[A-Z].*".toRegex()) -> {
//                    return false
//                }
                strLogin.matches(".*[!@#$%^&*+=/?].*".toRegex()) -> {
                    return false
                }
            }
            return true
        }

        fun isUserLoginNull(strLogin: String?): String? {
            if (strLogin.equals("")) {
                return null
            }
            return strLogin
        }
    }
}