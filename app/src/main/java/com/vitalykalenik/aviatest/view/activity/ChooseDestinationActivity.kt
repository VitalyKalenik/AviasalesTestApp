package com.vitalykalenik.aviatest.view.activity

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.vitalykalenik.aviatest.R
import com.vitalykalenik.aviatest.view.models.CityModel

/**
 * Экран с полями для ввода городов откуда/куда
 *
 * @author Vitaly Kalenik
 */
class ChooseDestinationActivity : AppCompatActivity() {

    private lateinit var chooseStartPointButton: Button
    private lateinit var chooseDestinationPointButton: Button
    private lateinit var searchButton: Button

    private var startCity: CityModel? = null
    private var destinationCity: CityModel? = null

    private val startCityResultContract =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                result.data?.extras?.getParcelable<CityModel>(CITY_EXTRA_KEY)?.also { city ->
                    startCity = city
                    chooseStartPointButton.text = city.fullName
                }
            }
        }

    private val destinationCityResultContract =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                result.data?.extras?.getParcelable<CityModel>(CITY_EXTRA_KEY)?.also { city ->
                    destinationCity = city
                    chooseDestinationPointButton.text = city.fullName
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_destination)

        title = getString(R.string.title_activity_search)
        initViews()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(START_CITY_BUNDLE_KEY, startCity)
        outState.putParcelable(DESTINATION_CITY_BUNDLE_KEY, destinationCity)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        savedInstanceState.getParcelable<CityModel>(START_CITY_BUNDLE_KEY)?.also { city ->
            startCity = city
            chooseStartPointButton.text = startCity?.fullName
        }
        savedInstanceState.getParcelable<CityModel>(DESTINATION_CITY_BUNDLE_KEY)?.also { city ->
            destinationCity = city
            chooseDestinationPointButton.text = destinationCity?.fullName
        }
    }

    private fun initViews() {
        chooseStartPointButton = findViewById(R.id.choose_start_button)
        chooseDestinationPointButton = findViewById(R.id.choose_destination_button)
        searchButton = findViewById(R.id.search_button)

        chooseStartPointButton.setOnClickListener {
            startCityResultContract.launch(SearchActivity.newIntent(this))
        }

        chooseDestinationPointButton.setOnClickListener {
            destinationCityResultContract.launch(SearchActivity.newIntent(this))
        }

        searchButton.setOnClickListener {
            if (startCity != null && destinationCity != null) {
                if (startCity == destinationCity) {
                    Toast.makeText(this, getString(R.string.choose_different_cities_error), Toast.LENGTH_LONG).show()
                } else {
                    startActivity(MapsActivity.newIntent(this, startCity!!, destinationCity!!))
                }
            } else {
                Toast.makeText(this, getString(R.string.cities_not_chosen_error), Toast.LENGTH_LONG).show()
            }
        }
    }

    companion object {

        const val CITY_EXTRA_KEY = "cityExtra"
        const val START_CITY_BUNDLE_KEY = "startCity"
        const val DESTINATION_CITY_BUNDLE_KEY = "destinationCity"
    }
}