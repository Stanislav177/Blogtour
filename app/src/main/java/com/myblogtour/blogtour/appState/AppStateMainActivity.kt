package com.myblogtour.blogtour.appState

import com.myblogtour.blogtour.utils.networkConnection.ConnectionNetwork

sealed class AppStateMainActivity {
    data class CurrentUser(val currentUser: Boolean) : AppStateMainActivity()
    data class NoCurrentUser(val currentUser: Boolean) : AppStateMainActivity()
    data class NetworkConnection(val connection: ConnectionNetwork) : AppStateMainActivity()
    data class NoNetworkConnection(val connection: Boolean) : AppStateMainActivity()
}