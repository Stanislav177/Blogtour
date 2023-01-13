package com.myblogtour.blogtour.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ProfileViewModel : ViewModel(), ProfileContract.ProfileViewModel {

    private val auth: FirebaseAuth by lazy {
        Firebase.auth
    }
    private val userCurrent = auth.currentUser

    override val userSuccess: LiveData<FirebaseUser> = MutableLiveData()
    override val userError: LiveData<Throwable> = MutableLiveData()
    override val userSingOut: LiveData<Boolean> = MutableLiveData()

    override fun onRefresh() {
        onLoadUserProfile()
    }

    override fun singInOut() {
        auth.signOut()
        userSingOut.mutable().postValue(true)
    }

    private fun onLoadUserProfile() {
        userSuccess.mutable().postValue(userCurrent)
    }

    private fun <T> LiveData<T>.mutable(): MutableLiveData<T> {
        return this as? MutableLiveData<T> ?: throw IllegalStateException("It is not Mutable")
    }

}