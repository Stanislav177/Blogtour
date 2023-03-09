package com.myblogtour.blogtour.ui.profileUser

import androidx.lifecycle.LiveData
import com.myblogtour.blogtour.domain.UserProfileEntity

interface ProfileContract {

    interface ProfileViewModel {
        val userSuccess: LiveData<UserProfileEntity>
        val userError: LiveData<Throwable>
        val userSingOut: LiveData<Boolean>
        val verificationEmail: LiveData<String>
        fun onRefresh()
        fun singInOut()
        fun verificationEmail()
    }
}