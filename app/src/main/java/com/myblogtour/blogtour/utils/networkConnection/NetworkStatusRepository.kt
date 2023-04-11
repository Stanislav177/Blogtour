package com.myblogtour.blogtour.utils.networkConnection

interface NetworkStatusRepository {
    fun networkStatus(
        onStatus: (ConnectionNetwork) -> Unit
    )
}