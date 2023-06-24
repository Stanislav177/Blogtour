package com.myblogtour.blogtour.ui.maps.searchMapAddress

import android.content.Context
import android.graphics.PointF
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.myblogtour.blogtour.R
import com.myblogtour.blogtour.databinding.FragmentSearchYandexMapsBinding
import com.myblogtour.blogtour.ui.maps.appStateMaps.AppStateSearchMapObj
import com.myblogtour.blogtour.ui.maps.dialogLocationMap.DialogLocationMap
import com.myblogtour.blogtour.utils.BaseFragment
import com.yandex.mapkit.MapKit
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.layers.GeoObjectTapEvent
import com.yandex.mapkit.layers.GeoObjectTapListener
import com.yandex.mapkit.layers.ObjectEvent
import com.yandex.mapkit.map.*
import com.yandex.mapkit.map.Map
import com.yandex.mapkit.search.SearchFactory
import com.yandex.mapkit.user_location.UserLocationLayer
import com.yandex.mapkit.user_location.UserLocationObjectListener
import com.yandex.mapkit.user_location.UserLocationView
import com.yandex.runtime.image.ImageProvider
import org.koin.androidx.viewmodel.ext.android.viewModel

class YandexMapsSearchFragment :
    BaseFragment<FragmentSearchYandexMapsBinding>(FragmentSearchYandexMapsBinding::inflate),
    UserLocationObjectListener,
    CameraListener, GeoObjectTapListener, InputListener {

    private lateinit var locationmapkit: UserLocationLayer
    private lateinit var mapObjCollection: MapObjectCollection
    private lateinit var mapKit: MapKit
    private var startSearch = false

    private val viewModelYandexSearch: YandexMapsSearchViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        MapKitFactory.initialize(requireActivity())
        SearchFactory.initialize(requireActivity())
        mapKit = MapKitFactory.getInstance()
        initLocationKit()
        initSearchClick()
        mapObjCollection = binding.mapview.map.mapObjects
        binding.mapview.map.addTapListener(this)
        binding.mapview.map.addInputListener(this)

        viewModelYandexSearch.getLiveData().observe(viewLifecycleOwner) {
            when (it) {
                is AppStateSearchMapObj.Error -> {
                    toast(it.error)
                }
                is AppStateSearchMapObj.Success -> {
                    for (searchResult in it.listGeo) {
                        val point = searchResult.obj!!.geometry[0].point
                        if (point != null) {
                            mapObjCollection.addPlacemark(Point(point.latitude, point.longitude),
                                ImageProvider.fromResource(requireActivity(),
                                    R.drawable.search_result))
                        }
                    }
                }
                is AppStateSearchMapObj.Address -> {
                    openDialogFragment(it.entityData.address!!, it.entityData.lat, it.entityData.lon)
                }
            }
        }
    }

    private fun initSearchClick() {
        binding.searchEdit.setOnEditorActionListener { _, i, _ ->
            if (i == EditorInfo.IME_ACTION_SEARCH) {
                startSearch = true
                viewModelYandexSearch.getSubmitQuery(binding.searchEdit.text.toString(),
                    VisibleRegionUtils.toPolygon(binding.mapview.map.visibleRegion))
                closeKeyBoard()
            }
            false
        }
    }

    private fun initLocationKit() {
        locationmapkit = mapKit.createUserLocationLayer(binding.mapview.mapWindow)
        locationmapkit.isVisible = true
        locationmapkit.setObjectListener(this)
        locationmapkit.isAutoZoomEnabled = true
        binding.mapview.map.addCameraListener(this@YandexMapsSearchFragment)
    }

    override fun onCameraPositionChanged(
        map: Map,
        cameraPOsition: CameraPosition,
        cameraUpdateReason: CameraUpdateReason,
        finished: Boolean,
    ) {
        if (finished) {
            if (startSearch) {
                if (binding.searchEdit.text.toString() != "") {
                    viewModelYandexSearch.getSubmitQuery(binding.searchEdit.text.toString(),
                        VisibleRegionUtils.toPolygon(binding.mapview.map.visibleRegion))
                }
            }
        }
    }

    override fun onObjectAdded(userLocationView: UserLocationView) {
        locationmapkit.setAnchor(PointF((binding.mapview.width() * 0.5).toFloat(),
            (binding.mapview.height() * 0.5).toFloat()),
            PointF((binding.mapview.width() * 0.5).toFloat(),
                (binding.mapview.height() * 0.83).toFloat())
        )
//        val picIcon = userLocationView.pin.useCompositeIcon()
//        picIcon.setIcon("icon",
//            ImageProvider.fromResource(requireActivity(), R.drawable.search_result),
//            IconStyle().setAnchor(
//                PointF(0f, 0f)
//            ).setRotationType(RotationType.ROTATE).setZIndex(0f).setScale(1f))
//        userLocationView.accuracyCircle.fillColor = Color.BLUE and -0x6600001
    }

    override fun onObjectRemoved(p0: UserLocationView) {

    }

    override fun onObjectUpdated(p0: UserLocationView, p1: ObjectEvent) {

    }

    private fun toast(mess: String) {
        Toast.makeText(requireActivity(), mess, Toast.LENGTH_SHORT).show()
    }

    override fun onStart() {
        binding.mapview.onStart()
        MapKitFactory.getInstance().onStart()
        super.onStart()
    }

    override fun onStop() {
        binding.mapview.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }

    private fun closeKeyBoard() {
        requireActivity().currentFocus?.let { view ->
            val imm =
                requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    override fun onObjectTap(geo: GeoObjectTapEvent): Boolean {
        val geoObjectSelection = geo.geoObject
            .metadataContainer.getItem(GeoObjectSelectionMetadata::class.java)
        val geometryList = geo.geoObject.geometry
        for (i in geometryList) {
            viewModelYandexSearch.getSubmitPoint(i.point!!)
            mapObjCollection.clear()
            mapObjCollection.addPlacemark(i.point!!,
                ImageProvider.fromResource(requireActivity(), R.drawable.search_result))
        }
        if (geoObjectSelection != null) {
            binding.mapview.map.selectGeoObject(geoObjectSelection.id,
                geoObjectSelection.layerId)
        }
        return true
    }

    override fun onMapTap(map: Map, point: Point) {
        binding.mapview.map.deselectGeoObject()
        mapObjCollection.clear()
        viewModelYandexSearch.getSubmitPoint(point)
        mapObjCollection.addPlacemark(point,
            ImageProvider.fromResource(requireActivity(), R.drawable.search_result))
    }

    private fun openDialogFragment(str: String, lat: Double, lon: Double) {
        DialogLocationMap.newInstance(str, lat, lon)
            .show(requireActivity().supportFragmentManager, "")
    }

    override fun onMapLongTap(p0: Map, p1: Point) {
    }
}