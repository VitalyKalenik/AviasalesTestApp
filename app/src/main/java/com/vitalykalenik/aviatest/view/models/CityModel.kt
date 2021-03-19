package com.vitalykalenik.aviatest.view.models

import android.os.Parcelable
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.parcel.Parcelize

/**
 * Город
 *
 * @constructor
 * @property countryCode Код страны
 * @property country Страна
 * @property latinFullName Полное название на английском
 * @property fullname Полное название в формате "Город, страна"
 * @property latLng Широта и долгота
 * @property city Город
 * @property latinCity Название города на английском
 * @property latinCountry Страна на латинском
 * @property shortName Сокращения названия города
 *
 * @author Vitaly Kalenik
 */
@Parcelize
data class CityModel (

    val countryCode: String? = "",

    val country: String? = "",

    val latinFullName: String? = "",

    val fullName: String? = "",

    val latLng: LatLng = LatLng(0.0, 0.0),

    val city: String? = "",

    val latinCity: String? = "",

    val latinCountry: String? = "",

    val shortName: List<String> = emptyList(),

) : Parcelable