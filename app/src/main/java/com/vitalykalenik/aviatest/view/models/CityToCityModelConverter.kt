package com.vitalykalenik.aviatest.view.models

import com.google.android.gms.maps.model.LatLng
import com.vitalykalenik.aviatest.domain.models.City
import com.vitalykalenik.aviatest.domain.models.Coordinates
import com.vitalykalenik.aviatest.domain.models.OneWayConverter

/**
 * Конвертер из [City] в [CityModel]
 *
 * @author Vitaly Kalenik
 */
class CityToCityModelConverter : OneWayConverter<City, CityModel>() {

    override fun convert(from: City): CityModel = CityModel(
        countryCode = from.countryCode,
        country = from.country,
        latinFullName = from.latinFullName,
        fullName = from.fullname,
        latLng = from.coordinates.toLatLng(),
        city = from.city,
        latinCity = from.latinCity,
        latinCountry = from.latinCountry,
        shortName = from.shortName
    )

    private fun Coordinates.toLatLng(): LatLng = LatLng(latitude, longitude)
}