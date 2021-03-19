package com.vitalykalenik.aviatest.domain

import com.vitalykalenik.aviatest.domain.models.City
import io.reactivex.Single

/**
 * Репозиторий приложения
 *
 * @author Vitaly Kalenik
 */
interface AviaRepository {

    /**
     * Получить список городов по текущему запросу [request]
     *
     * @param request Поисковый запрос
     */
    fun getCities(request: String) : Single<List<City>>
}