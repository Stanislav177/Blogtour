package com.myblogtour.blogtour.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.myblogtour.blogtour.appState.AppStateMainActivity
import com.myblogtour.blogtour.domain.repository.AuthFirebaseRepository
import com.myblogtour.blogtour.utils.SingleLiveEvent

class MainViewModel(
    private val authFirebaseRepository: AuthFirebaseRepository,
    private val liveData: MutableLiveData<AppStateMainActivity> = SingleLiveEvent<AppStateMainActivity>()
) : ViewModel() {

    fun getLiveData() = liveData

    fun onRefresh() {
        authFirebaseRepository.userCurrent(
            onSuccess = {
                liveData.postValue(AppStateMainActivity.CurrentUser(true))
            },
            onError = {
                liveData.postValue(AppStateMainActivity.NoCurrentUser(false))
            }
        )
    }
}