package com.myblogtour.blogtour.utils.checkPermission

import android.content.Context
import android.location.Address
import android.location.Geocoder

class RepositoryLocationAddressImpl(private val context: Context) :
    RepositoryLocationAddress {

    override fun getAddress(
        lat: Double?,
        lon: Double?,
        onAddress: (String) -> Unit,
        errorAddress: (String) -> Unit,
    ) {
        Thread {
            if (lat != null || lon != null) {
                lat?.let {
                    lon?.let {
                        onAddress.invoke(getAddressGeo(getGeocoder(lat, lon)))
                    }
                }
            } else {
                errorAddress.invoke("")
            }
        }.start()
    }

    private fun getGeocoder(lat: Double, lon: Double) =
        Geocoder(context).getFromLocation(lat, lon, 1)


    private fun getAddressGeo(listLoc: List<Address>?): String {
        var address = ""
        listLoc?.let {
            if (it.isNotEmpty()) {
                address = it[0].locality
                if (it[0].thoroughfare != null) {
                    address += ", " + it[0].thoroughfare
                    return if (it[0].subThoroughfare != null) {
                        address += ", " + it[0].subThoroughfare
                        address
                    } else {
                        address
                    }
                }
                return address
            }
        }
        return "Не определено"
    }
}