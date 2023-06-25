package com.myblogtour.blogtour.ui.maps

import android.content.Context
import android.os.Bundle
import android.view.View
import com.myblogtour.blogtour.R
import com.myblogtour.blogtour.databinding.FragmentYandexMapsBinding
import com.myblogtour.blogtour.utils.BaseFragment
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.layers.ObjectEvent
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.user_location.UserLocationObjectListener
import com.yandex.mapkit.user_location.UserLocationView
import com.yandex.runtime.image.ImageProvider

class YandexMapsFragment :
    BaseFragment<FragmentYandexMapsBinding>(FragmentYandexMapsBinding::inflate),
    UserLocationObjectListener {

    private var lon = 54.0
    private var lat = 37.0

    override fun onAttach(context: Context) {
        super.onAttach(context)
        arguments?.getDouble("LAT")?.let {
            lat = it
        }
        arguments?.getDouble("LON")?.let {
            lon = it
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        MapKitFactory.initialize(requireActivity())
        with(binding) {
            val mapObjCollection = mapview.map.mapObjects
            mapObjCollection.clear()
            mapObjCollection.addPlacemark(Point(lat, lon),
                ImageProvider.fromResource(requireActivity(), R.drawable.search_result))
            mapview.map.move(CameraPosition(Point(lat, lon), 16.0f, 0f, 0f),
                Animation(Animation.Type.SMOOTH, 2f),
                null)
        }
        initBtnZoomMaps()
    }

    private fun initBtnZoomMaps() {
        with(binding) {
            zoomPlusMaps.setOnClickListener {
                mapview.map.move(CameraPosition(mapview.map.cameraPosition.target,
                    mapview.map.cameraPosition.zoom + 1,
                    0.0f,
                    0.0f), Animation(Animation.Type.SMOOTH, 1f), null)
            }
            zoomMinusMaps.setOnClickListener {
                mapview.map.move(CameraPosition(mapview.map.cameraPosition.target,
                    mapview.map.cameraPosition.zoom - 1,
                    0.0f,
                    0.0f), Animation(Animation.Type.SMOOTH, 1f), null)
            }
        }
    }


    override fun onStart() {
        binding.mapview.onStart()
        MapKitFactory.getInstance().onStart()
        super.onStart()
    }

    override fun onStop() {
        binding.mapview.onStart()
        MapKitFactory.getInstance().onStart()
        super.onStop()
    }

    companion object {
        fun newInstance(lat: Double, lon: Double) = YandexMapsFragment().apply {
            arguments = Bundle().apply {
                putDouble("LAT", lat)
                putDouble("LON", lon)
            }
        }
    }

    override fun onObjectAdded(p0: UserLocationView) {
        TODO("Not yet implemented")
    }

    override fun onObjectRemoved(p0: UserLocationView) {
        TODO("Not yet implemented")
    }

    override fun onObjectUpdated(p0: UserLocationView, p1: ObjectEvent) {
        TODO("Not yet implemented")
    }
}