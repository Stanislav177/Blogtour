package com.myblogtour.blogtour.ui.maps

import android.content.Context
import android.os.Bundle
import android.view.View
import com.myblogtour.blogtour.databinding.FragmentYandexMapsBinding
import com.myblogtour.blogtour.utils.BaseFragment
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition

class YandexMapsFragment :
    BaseFragment<FragmentYandexMapsBinding>(FragmentYandexMapsBinding::inflate) {

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
            mapview.map.move(CameraPosition(Point(lat, lon), 14.0f, 0f, 0f),
                Animation(Animation.Type.SMOOTH, 0f),
                null)
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
}