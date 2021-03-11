package com.vitalykalenik.aviatest.view.animation

import android.graphics.Path
import android.graphics.PathMeasure
import android.graphics.Point
import android.location.Location
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.Projection
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.vitalykalenik.aviatest.R

/**
 * Класс, который занимается отрисовкой пути, по которому полетит самолетик
 *
 * @author Vitaly Kalenik
 */
object PathDrawer {

    private const val MAX_DOTS_NUMBER = 50
    private const val DOT_STROKE_WIDTH = 0f

    /**
     * Создать [PathMeasure] на основе [startLatLng] и [destinationLatLng]
     */
    fun calculatePath(
        mapProjection: Projection,
        startLatLng: LatLng,
        destinationLatLng: LatLng
    ): PathMeasure = Path().let {
        val startPoint = mapProjection.toScreenLocation(startLatLng)
        val endPoint = mapProjection.toScreenLocation(destinationLatLng)
        it.moveTo(startPoint.x.toFloat(), startPoint.y.toFloat())

        val (firstControlPoint, secondControlPoint) = makeControlPoints(startPoint, endPoint)
        it.cubicTo(
            firstControlPoint.x.toFloat(), firstControlPoint.y.toFloat(),
            secondControlPoint.x.toFloat(), secondControlPoint.y.toFloat(),
            endPoint.x.toFloat(), endPoint.y.toFloat()
        )
        PathMeasure(it, false)
    }

    /**
     * Нарисовать путь, по которому летит самолет
     */
    fun GoogleMap.drawPlanePath(
        mapProjection: Projection,
        pathMeasure: PathMeasure,
        dotsRadius: Int,
        dotsMargin: Int,
        dotsColor: Int
    ) {
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
            addCircle(
                CircleOptions().center(dotLatLng)
                    .fillColor(dotsColor)
                    .radius(getRadiusInMeters(mapProjection, dotLatLng, dotsRadius))
                    .strokeWidth(DOT_STROKE_WIDTH)
            )
        }
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

    /**
     * Вычислить радиус точки по координатам [latLng] в метрах
     */
    private fun getRadiusInMeters(mapProjection: Projection, latLng: LatLng, dotsRadius: Int): Double {
        val firstPoint = mapProjection.toScreenLocation(latLng)
        firstPoint.x += dotsRadius * 2
        val second = mapProjection.fromScreenLocation(firstPoint)
        val radius = FloatArray(1)
        Location.distanceBetween(latLng.latitude, latLng.longitude, second.latitude, second.longitude, radius)
        return radius[0].toDouble()
    }
}