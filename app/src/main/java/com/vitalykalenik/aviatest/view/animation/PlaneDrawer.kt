package com.vitalykalenik.aviatest.view.animation

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.Projection
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.vitalykalenik.aviatest.R

/**
 * Класс, который занимается отрисовкой самолетика
 *
 * @author Vitaly Kalenik
 */
object PlaneDrawer {

    private const val CENTER_OF_MARKER = 0.5f

    /**
     * Добавить на карту маркер с самолетиком
     */
    fun GoogleMap.addPlaneMarker(
        resources: Resources,
        mapProjection: Projection,
        startLatLng: LatLng,
        destinationLatLng: LatLng
    ): Marker {
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.ic_plane)
        val origPoint = mapProjection.toScreenLocation(startLatLng)
        val destPoint = mapProjection.toScreenLocation(destinationLatLng)
        val planeBitmap = if (origPoint.x < destPoint.x) {
            bitmap
        } else {
            flip(bitmap)
        }

        return addMarker(
            MarkerOptions()
                .icon(BitmapDescriptorFactory.fromBitmap(planeBitmap))
                .zIndex(2f)
                .anchor(CENTER_OF_MARKER, CENTER_OF_MARKER)
                .position(startLatLng)
        )
    }

    /**
     * Горизонтально отразить bitmap [source]
     */
    private fun flip(source: Bitmap): Bitmap {
        val matrix = Matrix()
        matrix.preScale(1.0f, -1.0f)
        return Bitmap.createBitmap(source, 0, 0, source.width, source.height, matrix, true)
    }
}