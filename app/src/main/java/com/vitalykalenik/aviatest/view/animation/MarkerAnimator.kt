package com.vitalykalenik.aviatest.view.animation

import android.animation.ValueAnimator
import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Path
import android.graphics.PathMeasure
import android.graphics.Point
import android.location.Location
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.Projection
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.vitalykalenik.aviatest.R
import com.vitalykalenik.aviatest.models.City
import com.vitalykalenik.aviatest.models.Coordinates
import com.vitalykalenik.aviatest.utils.StringUtils

/**
 * Класс для анимирования самолетика
 *
 * @property map Карта
 * @property resources Ресурсы
 * @property context Контекст для создания вью
 * @property startCity Город отправления
 * @property destinationCity Город назначения
 *
 * @author Vitaly Kalenik
 */
class MarkerAnimator(
    private val map: GoogleMap,
    private val resources: Resources,
    private val context: Context,
    private val startCity: City,
    private val destinationCity: City
) {

    private lateinit var mapProjection: Projection
    private lateinit var pathMeasure: PathMeasure
    private lateinit var planeMarker: Marker
    private lateinit var animator: ValueAnimator

    private val dotsRadius: Int = resources.getDimensionPixelSize(R.dimen.animation_dots_radius)
    private val dotsMargin: Int = resources.getDimensionPixelSize(R.dimen.animation_dots_margin)
    private val dotsColor: Int = ContextCompat.getColor(context, R.color.black)

    private val startLatLng: LatLng = startCity.coordinates.toLatLng()
    private val destinationLatLng: LatLng = destinationCity.coordinates.toLatLng()

    /**
     * Стартануть анимацию
     */
    fun start() {
        moveToCurrentLocation()
        mapProjection = map.projection

        addStartAndDestinationPoints()
        addPlanePath()
        map.setOnMapLoadedCallback {
            addPlaneMarker()
            startAnimation()
        }
    }

    /**
     * Добавить на карту маркер с самолетиком
     */
    private fun addPlaneMarker() {
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.ic_plane)
        val origPoint = mapProjection.toScreenLocation(startLatLng)
        val destPoint = mapProjection.toScreenLocation(destinationLatLng)
        val planeBitmap = if (origPoint.x < destPoint.x) {
            bitmap
        } else {
            flip(bitmap)
        }

        planeMarker = map.addMarker(
            MarkerOptions()
                .icon(BitmapDescriptorFactory.fromBitmap(planeBitmap))
                .zIndex(2f)
                .anchor(CENTER_OF_MARKER, CENTER_OF_MARKER)
                .position(startLatLng)
        )
    }

    /**
     * Добавить баблы с названиями городов
     */
    private fun addStartAndDestinationPoints() {
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
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f)
            gravity = Gravity.CENTER
        }

        map.addCityMarker(
            textView, startLatLng, startCity.shortName.firstOrNull() ?: StringUtils.EMPTY
        )
        map.addCityMarker(
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

    /**
     * Нарисовать путь, по которому летит самолет
     */
    private fun addPlanePath() {
        val path = Path()
        val startPoint = mapProjection.toScreenLocation(startLatLng)
        val endPoint = mapProjection.toScreenLocation(destinationLatLng)
        path.moveTo(startPoint.x.toFloat(), startPoint.y.toFloat())

        val (firstControlPoint, secondControlPoint) = makeControlPoints(startPoint, endPoint)
        path.cubicTo(
            firstControlPoint.x.toFloat(), firstControlPoint.y.toFloat(),
            secondControlPoint.x.toFloat(), secondControlPoint.y.toFloat(),
            endPoint.x.toFloat(), endPoint.y.toFloat()
        )
        pathMeasure = PathMeasure(path, false)

        var count = (pathMeasure.length / (dotsRadius * 2 + dotsMargin)).toInt()
        if (count > MAX_DOTS_NUMBER) {
            count = MAX_DOTS_NUMBER
        }
        for (i in 0 until count) {
            val pos = FloatArray(2)
            val distance = pathMeasure.length / count * i + dotsRadius + dotsMargin
            pathMeasure.getPosTan(distance, pos, null)
            val screenPoint = Point(pos[0].toInt(), pos[1].toInt())

            val dotLatLng = mapProjection.fromScreenLocation(screenPoint)
            map.addCircle(
                CircleOptions().center(dotLatLng)
                    .fillColor(dotsColor)
                    .radius(getRadiusInMeters(dotLatLng))
                    .strokeWidth(DOT_STROKE_WIDTH)
            )
        }
    }

    /**
     * Переместить камеру на область анимации самолетика
     */
    private fun moveToCurrentLocation() {
        val bounds = LatLngBounds.Builder()
            .include(startLatLng)
            .include(destinationLatLng)
            .build()
        val dm = resources.displayMetrics
        val width = dm.widthPixels
        val height = dm.heightPixels
        val padding = (width * 0.15).toInt()
        map.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding))
    }

    /**
     * Вычислить точки изгиба кривой, по которой летит самолетик
     */
    private fun makeControlPoints(startPoint: Point, endPoint: Point): Pair<Point, Point> {
        val firstControlPoint: Point
        val secondControlPoint: Point

        val axis = Math.max(Math.abs(startPoint.x - endPoint.x), Math.abs(startPoint.y - endPoint.y))
        val diameter = axis / 2
        if (startPoint.y < endPoint.y) {
            if (startPoint.x < endPoint.x) {
                firstControlPoint = Point(startPoint.x, startPoint.y + diameter)
                secondControlPoint = Point(endPoint.x, endPoint.y - diameter)
                if (Math.abs(firstControlPoint.x - secondControlPoint.x) < diameter) {
                    firstControlPoint.x = startPoint.x + diameter
                    secondControlPoint.x = endPoint.x - diameter
                }
            } else {
                firstControlPoint = Point(startPoint.x, startPoint.y + diameter)
                secondControlPoint = Point(endPoint.x, endPoint.y - diameter)
                if (Math.abs(firstControlPoint.x - secondControlPoint.x) < diameter) {
                    firstControlPoint.x = startPoint.x - diameter
                    secondControlPoint.x = endPoint.x + diameter
                }
            }
        } else if (startPoint.y > endPoint.y) {
            if (startPoint.x <= endPoint.x) {
                firstControlPoint = Point(startPoint.x, startPoint.y - diameter)
                secondControlPoint = Point(endPoint.x, endPoint.y + diameter)
                if (Math.abs(firstControlPoint.x - secondControlPoint.x) < diameter) {
                    firstControlPoint.x = startPoint.x + diameter
                    secondControlPoint.x = endPoint.x - diameter
                }
            } else {
                firstControlPoint = Point(startPoint.x, startPoint.y - diameter)
                secondControlPoint = Point(endPoint.x, endPoint.y + diameter)
                if (Math.abs(firstControlPoint.x - secondControlPoint.x) < diameter) {
                    firstControlPoint.x = startPoint.x + diameter
                    secondControlPoint.x = endPoint.x - diameter
                }
            }
        } else {
            firstControlPoint = Point(startPoint.x + diameter, startPoint.y + diameter)
            secondControlPoint = Point(endPoint.x - diameter, endPoint.y - diameter)
        }

        return Pair(firstControlPoint, secondControlPoint)
    }

    private fun startAnimation() {
        animator = ValueAnimator.ofFloat(0f, MAX_PROGRESS)
            .setDuration(ANIMATION_DURATION)
        animator.interpolator = LinearInterpolator()
        animator.addUpdateListener {
            val value = it.animatedValue as Float
            val fraction = value / MAX_PROGRESS
            animateTo(fraction)
        }
        animator.start()
    }

    private fun animateTo(progress: Float) {
        val tan = FloatArray(2)
        val pos = FloatArray(2)
        pathMeasure.getPosTan(pathMeasure.length * progress, pos, tan)
        rotateAndPositionPlaneMarker(tan, pos)
    }

    /**
     * Вычислить радиус точки по координатам [latLng] в метрах
     */
    private fun getRadiusInMeters(latLng: LatLng): Double {
        val firstPoint = mapProjection.toScreenLocation(latLng)
        firstPoint.x += dotsRadius * 2
        val second = mapProjection.fromScreenLocation(firstPoint)
        val radius = FloatArray(1)
        Location.distanceBetween(latLng.latitude, latLng.longitude, second.latitude, second.longitude, radius)
        return radius[0].toDouble()
    }

    /**
     * Переместить и повернуть маркер с самолетиком
     */
    private fun rotateAndPositionPlaneMarker(tan: FloatArray, pos: FloatArray) {
        planeMarker.rotation = Math.toDegrees(Math.atan2(tan[1].toDouble(), tan[0].toDouble())).toFloat()
        val mapped = mapProjection.fromScreenLocation(Point(pos[0].toInt(), pos[1].toInt()))
        if (mapped != null) {
            planeMarker.position = mapped
        }
    }

    /**
     * Горизонтально отразить bitmap [source]
     */
    private fun flip(source: Bitmap): Bitmap {
        val matrix = Matrix()
        matrix.preScale(1.0f, -1.0f)
        return Bitmap.createBitmap(source, 0, 0, source.width, source.height, matrix, true)
    }

    private fun Coordinates.toLatLng(): LatLng = LatLng(latitude, longitude)

    companion object {

        private const val ANIMATION_DURATION = 4000L
        private const val CENTER_OF_MARKER = 0.5f
        private const val MAX_DOTS_NUMBER = 50
        private const val DOT_STROKE_WIDTH = 0f
        private const val MAX_PROGRESS = 1000f
    }
}