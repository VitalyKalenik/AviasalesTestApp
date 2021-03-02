package com.vitalykalenik.aviatest.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Координаты
 *
 * @property latitude Широта
 * @property longitude Долгота
 *
 * @author Vitaly Kalenik
 */
@Parcelize
data class Coordinates(

    @SerializedName("lat")
    val latitude: Double,

    @SerializedName("lon")
    val longitude: Double

) : Parcelable