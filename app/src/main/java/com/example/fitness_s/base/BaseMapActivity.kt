package com.example.fitness_s.base

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Handler
import android.os.Looper
import com.example.fitness_s.R
import com.example.fitness_s.utils.MapUtils
import com.example.fitness_s.utils.PermissionUtils
import com.mapbox.core.constants.Constants
import com.mapbox.geojson.LineString
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions
import com.mapbox.mapboxsdk.location.modes.CameraMode
import com.mapbox.mapboxsdk.location.modes.RenderMode
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.Style
import com.mapbox.mapboxsdk.plugins.annotation.Symbol
import com.mapbox.mapboxsdk.plugins.annotation.SymbolManager
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource

abstract class BaseMapActivity: SupportMapActivity() {


    protected var symbolManager: SymbolManager? = null
    private var symbol: Symbol? = null


    override fun onMapLoaded(mapBoxMap: MapboxMap, style: Style) {
        setupListeners(mapBoxMap)
        loadImages(style)
        initSource(style)
        initLayer(style)
        mapView?.let {
            symbolManager = SymbolManager(it, mapBoxMap, style)
        }
        if (PermissionUtils.requestLocationPermission(this))
            showUserLocation()
    }

    private fun initLayer(style: Style) {
        val layer = MapUtils.createLayer(
            layerName = LINE_LAYER,
            sourceName = LINE_SOURCE
        )
        style.addLayer(layer)
    }

    private fun initSource(style: Style) {
        style.addSource(MapUtils.getSource(LINE_SOURCE))

    }

    protected fun getDirections(latLng: LatLng) {
        val location = map?.locationComponent?.lastKnownLocation
        MapUtils.getDirections(location, latLng) { data ->
            val source = map?.style?.getSourceAs<GeoJsonSource>(LINE_SOURCE)
            source.let { geoJsonSource ->
                data?.geometry()?.let {
                    source?.setGeoJson(
                        LineString.fromPolyline(
                            it,
                            Constants.PRECISION_6
                        )
                    )

                }
            }
        }
    }


    private fun setupListeners(mapBoxMap: MapboxMap) {
        mapBoxMap.addOnMapClickListener {
            getDirections(it)
            addMarker(it)
            return@addOnMapClickListener true
        }
    }

    private fun loadImages(style: Style) {
        MapUtils.addImage(
            style,
            MARKER_IMAGE,
            resources.getDrawable(R.drawable.ic_launcher_background)
        )
    }

    protected fun addMarker(latLng: LatLng) {
        symbol?.let { symbolManager?.delete(it) }
        val symbolOptions = MapUtils.createSymbol(latLng, MARKER_IMAGE)
        symbol = symbolManager?.create(symbolOptions)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PermissionUtils.LOCATION_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showUserLocation()
            }
        }
    }

    @SuppressLint("MissingPermission", "Range")
    private fun showUserLocation() {   //3) геолакац
        map?.style?.let {
            val locationComponents = map?.locationComponent
            locationComponents?.activateLocationComponent(
                LocationComponentActivationOptions.builder(applicationContext, it)
                    .build()
            )
            locationComponents?.isLocationComponentEnabled = true
            locationComponents?.cameraMode = CameraMode.TRACKING
            locationComponents?.renderMode = RenderMode.COMPASS
            val location = locationComponents?.lastKnownLocation
            val latlng = MapUtils.locationToLatLng(location)
            animateCamera(latlng)
        }
    }


    private fun animateCamera(latLng: LatLng, zoom: Double = CAMERA_ZOOM) {

        Handler(Looper.getMainLooper()).postDelayed({
            map?.animateCamera(
                MapUtils.getCameraPosition(latLng, zoom),
                DURATION_CAMERA
            )
        }, 6000)
    }

    companion object {
        private const val MARKER_IMAGE = "MARKER_IMAGE"
        private const val LINE_SOURCE = "LINE_SOURCE"
        private const val DURATION_CAMERA = 3000
        private const val LINE_LAYER = "LINE_LAYER"
        private const val CAMERA_ZOOM = 17.0
    }

}