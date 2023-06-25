package com.myblogtour.blogtour.ui.maps.appStateMaps

import com.myblogtour.blogtour.ui.maps.data.EntityAddress
import com.yandex.mapkit.GeoObjectCollection

sealed class AppStateSearchMapObj {
    data class Success(val listGeo: List<GeoObjectCollection.Item>) : AppStateSearchMapObj()
    data class Error(val error: String) : AppStateSearchMapObj()
    data class Address(val entityData: EntityAddress) : AppStateSearchMapObj(
    )
}