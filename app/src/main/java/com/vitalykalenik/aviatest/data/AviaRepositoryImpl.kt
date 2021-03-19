package com.vitalykalenik.aviatest.data

import com.vitalykalenik.aviatest.data.api.AviaApi
import com.vitalykalenik.aviatest.domain.AviaRepository
import com.vitalykalenik.aviatest.domain.models.City
import io.reactivex.Single
import java.util.Locale
import javax.inject.Inject

/**
 * Реализация [AviaRepository]
 *
 * @property api Апи для связи с сервером
 *
 * @author Vitaly Kalenik
 */
class AviaRepositoryImpl @Inject constructor(private val api: AviaApi) : AviaRepository {

    override fun getCities(request: String): Single<List<City>> =
        api.getCities(request, getSystemLanguage())
            .map { it.cities }

    /**
     * Если язык системы русский, то и ответ от сервера просим на русском, в любом другом случае на английском
     */
    private fun getSystemLanguage(): String = if (Locale.getDefault().language == RU_LANG) RU_LANG else EN_LANG

    companion object {

        private const val RU_LANG = "ru"
        private const val EN_LANG = "en"
    }
}