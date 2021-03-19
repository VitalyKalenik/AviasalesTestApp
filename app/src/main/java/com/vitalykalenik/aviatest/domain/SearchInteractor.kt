package com.vitalykalenik.aviatest.domain

import com.vitalykalenik.aviatest.domain.models.City
import io.reactivex.Single

/**
 * Интерактор для получения результатов поиска
 *
 * @author Vitaly Kalenik
 */
interface SearchInteractor {

    /**
     * Получить список городов по текущему запросу
     *
     * @param request Поисковый запрос
     */
    fun getCities(request: String) : Single<List<City>>
}