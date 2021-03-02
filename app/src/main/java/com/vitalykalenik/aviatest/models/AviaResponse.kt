package com.vitalykalenik.aviatest.models

import com.google.gson.annotations.SerializedName

/**
 * Ответ от сервера
 *
 * @property cities Список городов
 *
 * @author Vitaly Kalenik
 */
data class AviaResponse (

	@SerializedName("cities")
	val cities : List<City>
)