package com.vitalykalenik.aviatest.domain.models

import com.google.gson.annotations.SerializedName

/**
 * Город
 *
 * @constructor
 * @property countryCode Код страны
 * @property country Страна
 * @property latinFullName Полное название на английском
 * @property fullname Полное название в формате "Город, страна"
 * @property coordinates Координаты
 * @property city Город
 * @property latinCity Название города на английском
 * @property latinCountry Страна на латинском
 * @property shortName Сокращения названия города
 *
 * @author Vitaly Kalenik
 */
data class City(

    @SerializedName("countryCode")
    val countryCode: String? = "",

    @SerializedName("country")
    val country: String? = "",

    @SerializedName("latinFullName")
    val latinFullName: String? = "",

    @SerializedName("fullname")
    val fullname: String? = "",

    @SerializedName("location")
    val coordinates: Coordinates = Coordinates(0.0, 0.0),

    @SerializedName("city")
    val city: String? = "",

    @SerializedName("latinCity")
    val latinCity: String? = "",

    @SerializedName("latinCountry")
    val latinCountry: String? = "",

    @SerializedName("iata")
    val shortName: List<String> = emptyList(),

)