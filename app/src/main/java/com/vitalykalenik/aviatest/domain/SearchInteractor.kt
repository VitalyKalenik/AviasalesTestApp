package com.vitalykalenik.aviatest.domain

import com.vitalykalenik.aviatest.models.AviaResponse
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
    fun getCities(request: String) : Single<AviaResponse>
}