package com.vitalykalenik.aviatest.domain.models

import com.google.gson.annotations.SerializedName

/**
 * Координаты
 *
 * @property latitude Широта
 * @property longitude Долгота
 *
 * @author Vitaly Kalenik
 */
data class Coordinates(

    @SerializedName("lat")
    val latitude: Double,

    @SerializedName("lon")
    val longitude: Double

)