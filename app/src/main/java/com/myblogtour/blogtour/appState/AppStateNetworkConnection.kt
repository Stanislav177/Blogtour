package com.myblogtour.blogtour.appState

import com.myblogtour.blogtour.utils.networkConnection.ConnectionNetwork

sealed class AppStateNetworkConnection {
    data class OnConnectionNetwork(val connectionNetwork: ConnectionNetwork) :
        AppStateNetworkConnection()
}
