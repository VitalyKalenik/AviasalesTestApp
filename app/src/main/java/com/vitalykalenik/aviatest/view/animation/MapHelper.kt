package com.vitalykalenik.aviatest.view.animation

import android.content.res.Resources
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds

/**
 * Класс, который помогает настроить карту перед анимацией
 *
 * @author Vitaly Kalenik
 */
object MapHelper {

    /**
     * Подготовить карту
     * Двигает камеру карты к данным точкам [startLatLng] и [destinationLatLng]
     */
    fun GoogleMap.prepareMap(resources: Resources, startLatLng: LatLng, destinationLatLng: LatLng) {
        setupMap()
        val bounds = LatLngBounds.Builder()
            .include(startLatLng)
            .include(destinationLatLng)
            .build()
        val dm = resources.displayMetrics
        val width = dm.widthPixels
        val height = dm.heightPixels
        val padding = (width * 0.15).toInt()
        moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding))
    }

    private fun GoogleMap.setupMap() = uiSettings.apply{
        setAllGesturesEnabled(false)
        isCompassEnabled = false
        isZoomControlsEnabled = false
        isMapToolbarEnabled = false
    }
}