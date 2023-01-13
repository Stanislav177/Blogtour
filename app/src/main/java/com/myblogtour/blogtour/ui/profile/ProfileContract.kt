package com.myblogtour.blogtour.ui.profile

import androidx.lifecycle.LiveData
import com.google.firebase.auth.FirebaseUser

interface ProfileContract {

    interface ProfileViewModel {
        val userSuccess: LiveData<FirebaseUser>
        val userError: LiveData<Throwable>
        val userSingOut: LiveData<Boolean>
        fun onRefresh()
        fun singInOut()
    }
}