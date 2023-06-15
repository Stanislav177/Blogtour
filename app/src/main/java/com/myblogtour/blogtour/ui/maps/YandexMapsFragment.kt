package com.myblogtour.blogtour.ui.maps

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        MapKitFactory.initialize(requireActivity())
        with(binding) {
            mapview.map.move(CameraPosition(Point(37.0, 34.0), 11.0f, 0f, 0f),
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
}