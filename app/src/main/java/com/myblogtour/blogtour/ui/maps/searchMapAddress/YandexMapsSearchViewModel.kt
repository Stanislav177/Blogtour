package com.myblogtour.blogtour.ui.maps.searchMapAddress

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.myblogtour.blogtour.ui.maps.appStateMaps.AppStateSearchMapObj
import com.myblogtour.blogtour.ui.maps.data.EntityAddress
import com.myblogtour.blogtour.ui.maps.data.ResponsePoint
import com.myblogtour.blogtour.ui.maps.repository.RepositorySearchObjMap
import com.yandex.mapkit.geometry.Geometry
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.search.Address
import com.yandex.mapkit.search.ToponymObjectMetadata

class YandexMapsSearchViewModel(
    private val repositorySearchObjMap: RepositorySearchObjMap,
    private val liveData: MutableLiveData<AppStateSearchMapObj> = MutableLiveData(),
) : ViewModel() {

    fun getLiveData() = liveData

    fun getSubmitQuery(
        query: String,
        geometry: Geometry,
    ) {
        repositorySearchObjMap.submitQuery(query, geometry,
            onSuccess = {
                liveData.postValue(AppStateSearchMapObj.Success(it))
            },
            onError = {
                liveData.postValue(AppStateSearchMapObj.Error(it))
            })
    }

    fun getSubmitPoint(point: Point) {
        repositorySearchObjMap.submitPoint(point,
            onSuccess = {
                val address = converterResponseToAddress(it)
                if (address != null) {
                    liveData.postValue(AppStateSearchMapObj.Address(address))
                } else {
                    liveData.postValue(AppStateSearchMapObj.Error("Что-то пошло не так"))
                }
            },
            onError = {
                liveData.postValue(AppStateSearchMapObj.Error(it))
            })

    }

    private fun converterResponseToAddress(responsePoint: ResponsePoint): EntityAddress? {
        val lon = responsePoint.point.longitude
        val lat = responsePoint.point.latitude
        val strBuilder: StringBuilder = StringBuilder()
        val city = responsePoint.response.collection.children.firstOrNull()?.obj
            ?.metadataContainer
            ?.getItem(ToponymObjectMetadata::class.java)
            ?.address
            ?.components
            ?.firstOrNull { it.kinds.contains(Address.Component.Kind.LOCALITY) }
            ?.name
        val street = responsePoint.response.collection.children.firstOrNull()?.obj
            ?.metadataContainer
            ?.getItem(ToponymObjectMetadata::class.java)
            ?.address
            ?.components
            ?.firstOrNull { it.kinds.contains(Address.Component.Kind.STREET) }
            ?.name
        val house = responsePoint.response.collection.children.firstOrNull()?.obj
            ?.metadataContainer
            ?.getItem(ToponymObjectMetadata::class.java)
            ?.address
            ?.components
            ?.firstOrNull { it.kinds.contains(Address.Component.Kind.HOUSE) }
            ?.name

        if (city != null) {
            strBuilder.append("$city, ")
            return if (street != null) {
                strBuilder.append("$street ")
                if (house != null) {
                    strBuilder.append("$house")
                    EntityAddress(strBuilder.toString(), lon, lat)
                } else {
                    EntityAddress(strBuilder.toString(), lon, lat)
                }
            } else {
                EntityAddress(strBuilder.toString(), lon, lat)
            }
        }
        return null
    }
}