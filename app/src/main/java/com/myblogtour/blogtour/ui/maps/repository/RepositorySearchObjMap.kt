package com.myblogtour.blogtour.ui.maps.repository

import com.yandex.mapkit.GeoObjectCollection
import com.yandex.mapkit.geometry.Geometry
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.search.Response

interface RepositorySearchObjMap {
    fun submitQuery(
        query: String,
        geometry: Geometry,
        onSuccess: (List<GeoObjectCollection.Item>) -> Unit,
        onError: (String) -> Unit,
    )

    fun submitPoint(
        point: Point,
        onSuccess: (Response) -> Unit,
        onError: (String) -> Unit,
    )
}