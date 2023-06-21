package com.myblogtour.blogtour.ui.maps.appStateMaps

import com.yandex.mapkit.GeoObjectCollection

sealed class AppStateSearchMapObj {
    data class Success(val listGeo: List<GeoObjectCollection.Item>) : AppStateSearchMapObj()
    data class Error(val error: String) : AppStateSearchMapObj()
    data class Address(val address: String) : AppStateSearchMapObj()
}