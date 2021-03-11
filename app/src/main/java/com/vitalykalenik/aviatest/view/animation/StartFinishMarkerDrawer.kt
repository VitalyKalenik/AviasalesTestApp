package com.vitalykalenik.aviatest.view.animation

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.vitalykalenik.aviatest.R
import com.vitalykalenik.aviatest.models.City
import com.vitalykalenik.aviatest.utils.StringUtils

/**
 * Класс, который занимается отрисовкой баблов начального и конечного пункта маршрута самолетика
 *
 * @author Vitaly Kalenik
 */
object StartFinishMarkerDrawer {

    private const val CENTER_OF_MARKER = 0.5f
    private const val CITY_MARKER_TEXT_SIZE = 20f

    /**
     * Добавить баблы с названиями городов
     */
    fun GoogleMap.addStartAndDestinationPoints(
        context: Context,
        startLatLng: LatLng,
        destinationLatLng: LatLng,
        startCity: City,
        destinationCity: City
    ) {
        val textView = TextView(context).apply {
            val width = resources.getDimensionPixelSize(R.dimen.map_city_marker_width)
            val height = resources.getDimensionPixelSize(R.dimen.map_city_marker_height)
            layoutParams = ViewGroup.LayoutParams(width, height)
            measure(
                View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY)
            )
            layout(0, 0, measuredWidth, measuredHeight)
            background = ContextCompat.getDrawable(context, R.drawable.map_marker)
            setTextColor(ContextCompat.getColor(context, android.R.color.white))
            isAllCaps = true
            setTextSize(TypedValue.COMPLEX_UNIT_SP, CITY_MARKER_TEXT_SIZE)
            gravity = Gravity.CENTER
        }

        addCityMarker(
            textView, startLatLng, startCity.shortName.firstOrNull() ?: StringUtils.EMPTY
        )
        addCityMarker(
            textView, destinationLatLng, destinationCity.shortName.firstOrNull() ?: StringUtils.EMPTY
        )
    }

    /**
     * Добавить [Marker] из [textView] в координатах [latLng] с текстом [name]
     */
    private fun GoogleMap.addCityMarker(textView: TextView, latLng: LatLng, name: String) {
        val originBitmap = Bitmap.createBitmap(
            textView.measuredWidth,
            textView.measuredHeight,
            Bitmap.Config.ARGB_8888
        )
        val originCanvas = Canvas(originBitmap)
        textView.text = name
        textView.draw(originCanvas)

        addMarker(
            MarkerOptions()
                .position(latLng)
                .icon(BitmapDescriptorFactory.fromBitmap(originBitmap))
                .anchor(CENTER_OF_MARKER, CENTER_OF_MARKER)
        )
    }
}