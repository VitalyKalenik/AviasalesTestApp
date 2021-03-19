package com.vitalykalenik.aviatest.data.models

import com.google.gson.annotations.SerializedName
import com.vitalykalenik.aviatest.domain.models.City

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