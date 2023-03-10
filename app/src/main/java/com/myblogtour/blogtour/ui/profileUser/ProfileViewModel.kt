package com.myblogtour.blogtour.ui.profileUser

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.myblogtour.blogtour.domain.UserProfileEntity
import com.myblogtour.blogtour.domain.repository.AuthFirebaseRepository
import com.myblogtour.blogtour.domain.repository.UserProfileRepository

class ProfileViewModel(
    private val userProfileRepository: UserProfileRepository,
    private val authFirebaseRepository: AuthFirebaseRepository
) : ViewModel(),
    ProfileContract.ProfileViewModel {

    override val userSuccess: LiveData<UserProfileEntity> = MutableLiveData()
    override val userError: LiveData<Throwable> = MutableLiveData()
    override val userSingOut: LiveData<Boolean> = MutableLiveData()
    override val verificationEmail: LiveData<String> = MutableLiveData()

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

    override fun verificationEmail() {
        authFirebaseRepository.verificationEmail {
            if (it) {
                verificationEmail.mutable().postValue("Проверьте почту для подтверждения Email")
            } else {
                verificationEmail.mutable().postValue("Что-то пошло не так")
            }
        }
    }

    private fun <T> LiveData<T>.mutable(): MutableLiveData<T> {
        return this as? MutableLiveData<T> ?: throw IllegalStateException("It is not Mutable")
    }
}