package com.myblogtour.blogtour.ui.maps

import android.graphics.Color
import android.graphics.PointF
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import com.myblogtour.blogtour.R
import com.myblogtour.blogtour.databinding.FragmentSearchYandexMapsBinding
import com.myblogtour.blogtour.utils.BaseFragment
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Geometry
import com.yandex.mapkit.geometry.PolylinePosition
import com.yandex.mapkit.layers.ObjectEvent
import com.yandex.mapkit.map.*
import com.yandex.mapkit.mapview.MapView
import com.yandex.mapkit.search.*
import com.yandex.mapkit.search.search_layer.*
import com.yandex.mapkit.user_location.UserLocationLayer
import com.yandex.mapkit.user_location.UserLocationObjectListener
import com.yandex.mapkit.user_location.UserLocationView
import com.yandex.runtime.Error
import com.yandex.runtime.image.ImageProvider

class Search :
    BaseFragment<FragmentSearchYandexMapsBinding>(FragmentSearchYandexMapsBinding::inflate),
    SearchLayer, CameraListener, UserLocationObjectListener {

    private lateinit var locationmapkit: UserLocationLayer
    private lateinit var mapView: MapView
    private lateinit var searchEdit: EditText
    private lateinit var searchManager: SearchManager
    private lateinit var searchSession: Session

    private fun submitQuery(query: String) {
        Toast.makeText(requireContext(), query, Toast.LENGTH_SHORT).show()
        searchSession = searchManager.submit(query,
            VisibleRegionUtils.toPolygon(mapView.map.visibleRegion),
            SearchOptions(),
            searchSL)
    }

    private val searchSL = object : Session.SearchListener {
        override fun onSearchResponse(p0: Response) {
            val mapObjects: MapObjectCollection = mapView.map.mapObjects
            mapObjects.clear()
            for (searchResult in p0.collection.children) {
                val resultLocation = searchResult.obj!!.geometry[0].point
                if (resultLocation != null) {
                    mapObjects.addPlacemark(
                        resultLocation
                    )
                }
            }
        }

        override fun onSearchError(p0: Error) {
            TODO("Not yet implemented")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        SearchFactory.initialize(requireActivity())
        searchManager = SearchFactory.getInstance().createSearchManager(SearchManagerType.COMBINED)
        val mapKit = MapKitFactory.getInstance()
        locationmapkit = mapKit.createUserLocationLayer(binding.mapview.mapWindow)
        locationmapkit.isVisible = true
        locationmapkit.setObjectListener(this)
        locationmapkit.isAutoZoomEnabled = true
        mapView = binding.mapview
        searchEdit = binding.searchEdit
        mapView.map.addCameraListener(this)
        searchEdit.setOnEditorActionListener { textView, i, keyEvent ->
            if (i == EditorInfo.IME_ACTION_SEARCH) {
                submitQuery(searchEdit.text.toString())
            }
            false
        }
    }

    override fun onStop() {
        mapView.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        mapView.onStart()
    }

    fun onSearchResponse(response: Response) {
        val mapObjects: MapObjectCollection = mapView.map.mapObjects
        mapObjects.clear()
        for (searchResult in response.collection.children) {
            val resultLocation = searchResult.obj!!.geometry[0].point
            if (resultLocation != null) {
                mapObjects.addPlacemark(
                    resultLocation,
                    ImageProvider.fromResource(requireActivity(), R.drawable.ic_like_on))
            }
        }
    }

    override fun onCameraPositionChanged(
        p0: com.yandex.mapkit.map.Map,
        p1: CameraPosition,
        p2: CameraUpdateReason,
        finished: Boolean,
    ) {
        if (finished) {
            submitQuery(searchEdit.text.toString())
        }
    }

    override fun submitQuery(p0: String, p1: SearchOptions) {
        TODO("Not yet implemented")
    }

    override fun submitQuery(p0: String, p1: Geometry, p2: SearchOptions) {
        TODO("Not yet implemented")
    }

    override fun searchByUri(p0: String, p1: SearchOptions) {
        TODO("Not yet implemented")
    }

    override fun resubmit() {
        TODO("Not yet implemented")
    }

    override fun enableRequestsOnMapMoves(p0: Boolean) {
        TODO("Not yet implemented")
    }

    override fun enableMapMoveOnSearchResponse(p0: Boolean) {
        TODO("Not yet implemented")
    }

    override fun hasNextPage(): Boolean {
        TODO("Not yet implemented")
    }

    override fun fetchNextPage() {
        TODO("Not yet implemented")
    }

    override fun clear() {
        TODO("Not yet implemented")
    }

    override fun getSearchResultsList(): MutableList<SearchResultItem> {
        TODO("Not yet implemented")
    }

    override fun searchMetadata(): SearchMetadata? {
        TODO("Not yet implemented")
    }

    override fun setSearchManager(p0: SearchManager) {
        TODO("Not yet implemented")
    }

    override fun addSearchResultListener(p0: SearchResultListener) {
        TODO("Not yet implemented")
    }

    override fun removeSearchResultListener(p0: SearchResultListener) {
        TODO("Not yet implemented")
    }

    override fun addPlacemarkListener(p0: PlacemarkListener) {
        TODO("Not yet implemented")
    }

    override fun removePlacemarkListener(p0: PlacemarkListener) {
        TODO("Not yet implemented")
    }

    override fun setSortByDistance(p0: Geometry) {
        TODO("Not yet implemented")
    }

    override fun setPolylinePosition(p0: PolylinePosition) {
        TODO("Not yet implemented")
    }

    override fun resetSort() {
        TODO("Not yet implemented")
    }

    override fun setFilterCollection(p0: FilterCollection?) {
        TODO("Not yet implemented")
    }

    override fun setFilters(p0: MutableList<BusinessFilter>) {
        TODO("Not yet implemented")
    }

    override fun setAssetsProvider(p0: AssetsProvider) {
        TODO("Not yet implemented")
    }

    override fun resetAssetsProvider() {
        TODO("Not yet implemented")
    }

    override fun selectPlacemark(p0: String) {
        TODO("Not yet implemented")
    }

    override fun selectedPlacemarkId(): String? {
        TODO("Not yet implemented")
    }

    override fun deselectPlacemark() {
        TODO("Not yet implemented")
    }

    override fun forceUpdateIcon(
        p0: String,
        p1: PlacemarkIconType,
        p2: ImageProvider,
        p3: IconStyle,
    ) {
        TODO("Not yet implemented")
    }

    override fun forceUpdateMapObjects() {
        TODO("Not yet implemented")
    }

    override fun obtainAdIcons(p0: Boolean) {
        TODO("Not yet implemented")
    }

    override fun setInsets(p0: Int, p1: Int, p2: Int, p3: Int) {
        TODO("Not yet implemented")
    }

    override fun setVisible(p0: Boolean) {
        TODO("Not yet implemented")
    }

    override fun isValid(): Boolean {
        TODO("Not yet implemented")
    }

    override fun onObjectAdded(userLocationView: UserLocationView) {
        locationmapkit.setAnchor(PointF((binding.mapview.width() * 0.5).toFloat(),
            (binding.mapview.height() * 0.5).toFloat()),
            PointF((binding.mapview.width() * 0.5).toFloat(),
                (binding.mapview.height() * 0.83).toFloat())
        )
        val picIcon = userLocationView.pin.useCompositeIcon()
        picIcon.setIcon("icon",
            ImageProvider.fromResource(requireActivity(), R.drawable.search_result),
            IconStyle().setAnchor(
                PointF(0f, 0f)
            ).setRotationType(RotationType.ROTATE).setZIndex(0f).setScale(1f))
        userLocationView.accuracyCircle.fillColor = Color.BLUE and -0x6600001
    }

    override fun onObjectRemoved(p0: UserLocationView) {
        TODO("Not yet implemented")
    }

    override fun onObjectUpdated(p0: UserLocationView, p1: ObjectEvent) {

    }
}