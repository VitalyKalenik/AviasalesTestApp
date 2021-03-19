package com.vitalykalenik.aviatest.presentation.models

import com.google.android.gms.maps.model.LatLng
import com.vitalykalenik.aviatest.domain.models.City
import com.vitalykalenik.aviatest.domain.models.Coordinates
import com.vitalykalenik.aviatest.view.models.CityModel
import com.vitalykalenik.aviatest.view.models.CityToCityModelConverter
import junit.framework.Assert.assertEquals
import org.junit.Test

/**
 * Тест на [CityToCityModelConverter]
 *
 * @author Vitaly Kalenik
 */
class CityToCityModelConverterTest {

    private val converter = CityToCityModelConverter()

    @Test
    fun test() {
        assertEquals(buildCityModel(), converter.convert(buildCity()))
    }

    @Test
    fun `test list convert`() {
        assertEquals(listOf(buildCityModel()), converter.convertList(listOf(buildCity())))
    }

    private fun buildCityModel() = CityModel(
        countryCode = "",
        country = "",
        latinFullName = "",
        fullName = "",
        latLng = LatLng(0.0, 0.0),
        city = "",
        latinCity = "",
        latinCountry = "",
        shortName = emptyList(),
    )

    private fun buildCity() = City(
        countryCode = "",
        country = "",
        latinFullName = "",
        fullname = "",
        coordinates = Coordinates(0.0, 0.0),
        city = "",
        latinCity = "",
        latinCountry = "",
        shortName = emptyList(),
    )
}