package com.vitalykalenik.aviatest.data

import com.vitalykalenik.aviatest.data.api.AviaApi
import com.vitalykalenik.aviatest.domain.AviaRepository
import com.vitalykalenik.aviatest.models.AviaResponse
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

    override fun getCities(request: String): Single<AviaResponse> = api.getCities(request, getSystemLanguage())

    /**
     * Если язык системы русский, то и ответ от сервера просим на русском, в любом другом случае на английском
     */
    private fun getSystemLanguage(): String = if (Locale.getDefault().language == RU_LANG) RU_LANG else EN_LANG

    companion object {

        private const val RU_LANG = "ru"
        private const val EN_LANG = "en"
    }
}