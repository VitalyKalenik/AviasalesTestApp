package com.vitalykalenik.aviatest.view.animation

import android.animation.ValueAnimator
import android.content.Context
import android.content.res.Resources
import android.graphics.PathMeasure
import android.graphics.Point
import android.view.animation.LinearInterpolator
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.Projection
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.vitalykalenik.aviatest.R
import com.vitalykalenik.aviatest.models.City
import com.vitalykalenik.aviatest.models.Coordinates
import com.vitalykalenik.aviatest.view.animation.MapHelper.prepareMap
import com.vitalykalenik.aviatest.view.animation.PathDrawer.drawPlanePath
import com.vitalykalenik.aviatest.view.animation.PlaneDrawer.addPlaneMarker
import com.vitalykalenik.aviatest.view.animation.StartFinishMarkerDrawer.addStartAndDestinationPoints

/**
 * Класс для анимирования самолетика
 *
 * @property map Карта
 * @property resources Ресурсы
 * @property context Контекст для создания вью
 * @property startCity Город отправления
 * @property destinationCity Город назначения
 * @param startValue начальное положение самолетика относительно максимального значения
 *
 * @author Vitaly Kalenik
 */
class MarkerAnimator(
    private val map: GoogleMap,
    private val resources: Resources,
    private val context: Context,
    private val startCity: City,
    private val destinationCity: City,
    startValue: Float = 0f
) {

    /**
     * Текущий прогресс самолетика
     */
    val currentProgress: Float
        get() = animator.animatedValue as Float

    private lateinit var mapProjection: Projection
    private lateinit var pathMeasure: PathMeasure
    private lateinit var planeMarker: Marker
    private lateinit var animator: ValueAnimator

    private val startLatLng: LatLng = startCity.coordinates.toLatLng()
    private val destinationLatLng: LatLng = destinationCity.coordinates.toLatLng()

    private val dotsRadius: Int = resources.getDimensionPixelSize(R.dimen.animation_dots_radius)
    private val dotsMargin: Int = resources.getDimensionPixelSize(R.dimen.animation_dots_margin)
    private val dotsColor: Int = ContextCompat.getColor(context, R.color.black)

    init {
        initAnimator(startValue)
    }

    /**
     * Стартануть анимацию
     */
    fun start() {
        map.prepareMap(resources, startLatLng, destinationLatLng)
        mapProjection = map.projection
        pathMeasure = PathDrawer.calculatePath(mapProjection, startLatLng, destinationLatLng)

        map.apply {
            addStartAndDestinationPoints(context, startLatLng, destinationLatLng, startCity, destinationCity)
            drawPlanePath(mapProjection, pathMeasure, dotsRadius, dotsMargin, dotsColor)
            setOnMapLoadedCallback {
                planeMarker = addPlaneMarker(resources, mapProjection, startLatLng, destinationLatLng)
                startAnimation()
            }
        }
    }

    private fun initAnimator(startValue: Float) {
        if (startValue > MAX_PROGRESS) {
            return
        }
        val animationDuration = ANIMATION_DURATION - (startValue / MAX_PROGRESS * ANIMATION_DURATION).toLong()
        animator = ValueAnimator.ofFloat(startValue, MAX_PROGRESS).setDuration(animationDuration)
        animator.interpolator = LinearInterpolator()
        animator.addUpdateListener { animateTo(it) }
    }

    private fun startAnimation() {
        animator.start()
    }

    private fun animateTo(valueAnimator: ValueAnimator) {
        val value = valueAnimator.animatedValue as Float
        val fraction = value / MAX_PROGRESS
        val tan = FloatArray(2)
        val pos = FloatArray(2)
        pathMeasure.getPosTan(pathMeasure.length * fraction, pos, tan)
        rotateAndPositionPlaneMarker(tan, pos)
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

    private fun Coordinates.toLatLng(): LatLng = LatLng(latitude, longitude)

    companion object {

        private const val ANIMATION_DURATION = 4000L
        private const val MAX_PROGRESS = 1000f
    }
}