package com.myblogtour.blogtour.ui.maps.data

import android.os.Parcelable
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.search.Response
import kotlinx.android.parcel.Parcelize

@Parcelize
data class EntityAddress(
    val address: String?,
    val lon: Double,
    val lat: Double,
) : Parcelable

data class ResponsePoint(
    val point: Point,
    val response: Response
)
