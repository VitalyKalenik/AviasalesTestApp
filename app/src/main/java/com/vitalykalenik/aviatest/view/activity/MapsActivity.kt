package com.vitalykalenik.aviatest.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.vitalykalenik.aviatest.R
import com.vitalykalenik.aviatest.models.City
import com.vitalykalenik.aviatest.view.animation.MarkerAnimator

/**
 * Экран с анимацией летящего самолетика
 *
 * @author Vitaly Kalenik
 */
class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var map: GoogleMap
    private lateinit var animator: MarkerAnimator
    private var startValue = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        val startLocation = intent.getParcelableExtra<City>(START_LOCATION_EXTRA)
        val destinationLocation = intent.getParcelableExtra<City>(DESTINATION_LOCATION_EXTRA)

        animator = MarkerAnimator(
            map,
            resources,
            this,
            startLocation,
            destinationLocation,
            startValue
        )
        animator.start()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (::animator.isInitialized) {
            outState.putFloat(PLANE_PROGRESS_VALUE, animator.currentProgress)
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        startValue = savedInstanceState.getFloat(PLANE_PROGRESS_VALUE)
    }

    companion object {

        private const val START_LOCATION_EXTRA = "startLocationExtra"
        private const val DESTINATION_LOCATION_EXTRA = "destinationLocationExtra"
        private const val PLANE_PROGRESS_VALUE = "planeProgressValue"

        /**
         * Новый [Intent] с параметрами для старта этой активити
         *
         * @param context Контекст для старта активити
         * @param startCity Город отправления
         * @param destinationCity Город назначения
         */
        @JvmStatic
        fun newIntent(context: Context, startCity: City, destinationCity: City): Intent =
            Intent(
                context, MapsActivity::class.java
            ).apply {
                putExtra(START_LOCATION_EXTRA, startCity)
                putExtra(DESTINATION_LOCATION_EXTRA, destinationCity)
            }
    }
}