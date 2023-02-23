package com.myblogtour.blogtour.ui.profile

import androidx.lifecycle.LiveData
import com.myblogtour.blogtour.domain.PublicationEntity
import com.myblogtour.blogtour.domain.UserProfileEntity

interface ProfileContract {

    interface ProfileViewModel {
        val userSuccess: LiveData<UserProfileEntity>
        val userError: LiveData<Throwable>
        val userSingOut: LiveData<Boolean>
        fun onRefresh()
        fun singInOut()
    }
}