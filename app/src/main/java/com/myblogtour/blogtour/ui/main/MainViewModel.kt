package com.myblogtour.blogtour.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.myblogtour.blogtour.appState.AppStateMainActivity
import com.myblogtour.blogtour.domain.repository.AuthFirebaseRepository

class MainViewModel(
    private val authFirebaseRepository: AuthFirebaseRepository,
    private val liveData: MutableLiveData<AppStateMainActivity> = MutableLiveData()
) : ViewModel() {

    fun getLiveData() = liveData

    fun onRefresh() {
        authFirebaseRepository.userCurrent(
            onSuccess = {
                liveData.postValue(AppStateMainActivity.CurrentUser(true))
            },
            onError = {
                liveData.postValue(AppStateMainActivity.CurrentUser(false))
            }
        )
    }
}