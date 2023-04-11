package com.myblogtour.blogtour.ui.noNetworkConnection

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.myblogtour.blogtour.appState.AppStateNetworkConnection
import com.myblogtour.blogtour.utils.SingleLiveEvent
import com.myblogtour.blogtour.utils.networkConnection.NetworkStatusRepository

class NoNetworkConnectionViewModel(
    private val networkStatusRepository: NetworkStatusRepository,
    private val liveData: MutableLiveData<AppStateNetworkConnection> = SingleLiveEvent()
) :
    ViewModel() {

    fun getLiveData() = liveData

    fun updateNetworkConnection() {
        networkStatusRepository.networkStatus {
            liveData.postValue(AppStateNetworkConnection.OnConnectionNetwork(it))
        }
    }
}