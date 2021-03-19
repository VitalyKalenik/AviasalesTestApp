package com.vitalykalenik.aviatest.data.api

import com.vitalykalenik.aviatest.data.models.AviaResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Апи для связи с сервером
 *
 * @author Vitaly Kalenik
 */
interface AviaApi {

    /**
     * Получить список городов по текущему запросу [request]
     *
     * @param request Поисковый запрос
     * @param language Язык данных в ответе
     */
    @GET(AUTOCOMPLETE_REQUEST)
    fun getCities(@Query(QUERY_TERM) request: String, @Query(QUERY_LANGUAGE) language: String) : Single<AviaResponse>

    companion object{

        const val BASE_URL = "https://yasen.hotellook.com/"
        private const val AUTOCOMPLETE_REQUEST = "autocomplete"

        private const val QUERY_TERM = "term"
        private const val QUERY_LANGUAGE = "lang"
    }
}