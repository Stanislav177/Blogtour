package com.myblogtour.blogtour.utils.checkPermission

interface RepositoryLocationAddress {
    fun getAddress(
        lat: Double?, lon: Double?,
        onAddress: (String) -> Unit,
        errorAddress: (String) -> Unit,
    )
}