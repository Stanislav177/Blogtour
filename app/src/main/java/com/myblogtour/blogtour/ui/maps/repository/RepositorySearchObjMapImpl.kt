package com.myblogtour.blogtour.ui.maps.repository

import com.yandex.mapkit.GeoObjectCollection
import com.yandex.mapkit.geometry.Geometry
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.search.Response
import com.yandex.mapkit.search.SearchManager
import com.yandex.mapkit.search.SearchOptions
import com.yandex.mapkit.search.Session
import com.yandex.runtime.Error
import com.yandex.runtime.network.NetworkError
import com.yandex.runtime.network.RemoteError

class RepositorySearchObjMapImpl(
    private var searchManager: SearchManager,
) : RepositorySearchObjMap {

    private lateinit var searchSession: Session
    override fun submitQuery(
        query: String,
        geometry: Geometry,
        onSuccess: (List<GeoObjectCollection.Item>) -> Unit,
        onError: (String) -> Unit,
    ) {
        searchSession = searchManager.submit(query,
            geometry,
            SearchOptions(),
            object : Session.SearchListener {
                override fun onSearchResponse(response: Response) {
                    onSuccess.invoke(response.collection.children)
                }

                override fun onSearchError(error: Error) {
                    if (error is RemoteError) {
                        onError.invoke("Remote error")
                    } else if (error is NetworkError) {
                        onError.invoke("Network error")
                    }
                }
            })
    }

    override fun submitPoint(
        point: Point,
        onSuccess: (Response) -> Unit,
        onError: (String) -> Unit,
    ) {
        searchSession = searchManager
            .submit(point, 20, SearchOptions(), object : Session.SearchListener {
                override fun onSearchResponse(response: Response) {
                    onSuccess.invoke(response)
                }

                override fun onSearchError(error: Error) {
                    if (error is RemoteError) {
                        onError.invoke("Remote error")
                    } else if (error is NetworkError) {
                        onError.invoke("Network error")
                    }
                }
            })
    }
}