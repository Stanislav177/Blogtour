package com.myblogtour.blogtour.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.myblogtour.airtable.domain.UserProfileDTO
import com.myblogtour.blogtour.domain.UserProfileEntity
import com.myblogtour.blogtour.domain.repository.AuthFirebaseRepository
import com.myblogtour.blogtour.domain.repository.UserProfileRepository
import com.myblogtour.blogtour.utils.converterFromProfileUserDtoToProfileUserEntity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileViewModel(
    private val userProfileRepository: UserProfileRepository,
    private val authFirebaseRepository: AuthFirebaseRepository
) : ViewModel(),
    ProfileContract.ProfileViewModel {

    override val userSuccess: LiveData<UserProfileEntity> = MutableLiveData()
    override val userError: LiveData<Throwable> = MutableLiveData()
    override val userSingOut: LiveData<Boolean> = MutableLiveData()

    override fun onRefresh() {
        authFirebaseRepository.userCurrent(
            onSuccess = { fbUser ->
                userProfileRepository.getUserProfile(
                    fbUser.displayName!!,
                    onSuccess = {
                        userSuccess.mutable().postValue(it)
                    },
                    onError = {
                        userError.mutable().postValue(it)
                    }
                )
            },
            onError = {
                userError.mutable().postValue(IllegalStateException(it))
            }
        )
    }

    override fun singInOut() {
        authFirebaseRepository.singInOut {
            userSingOut.mutable().postValue(it)
        }

    }

    private fun <T> LiveData<T>.mutable(): MutableLiveData<T> {
        return this as? MutableLiveData<T> ?: throw IllegalStateException("It is not Mutable")
    }
}