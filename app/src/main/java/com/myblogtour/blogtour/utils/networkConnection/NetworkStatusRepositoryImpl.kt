package com.myblogtour.blogtour.utils.networkConnection

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

class NetworkStatusRepositoryImpl(private val context: Context) : NetworkStatusRepository {

    override fun networkStatus(onStatus: (ConnectionNetwork) -> Unit) {
        onStatus.invoke(isOnline())
    }

    private fun isOnline(): ConnectionNetwork {
        val l = ConnectionNetwork(false, "Нет соединения")
        try {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (connectivityManager != null) {
                val capabilities =
                    connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
                if (capabilities != null) {
                    if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                        return ConnectionNetwork(true, "TRANSPORT_CELLULAR")
                    } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                        return ConnectionNetwork(true, "TRANSPORT_WIFI")
                    } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_VPN)) {
                        return ConnectionNetwork(true, "TRANSPORT_VPN")
                    } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                        return ConnectionNetwork(true, "TRANSPORT_ETHERNET")
                    }
                }
            }
            return l
        } catch (_: Throwable) {

        }
        return l
    }
}