package com.vitalykalenik.aviatest.data

import com.vitalykalenik.aviatest.data.api.AviaApi
import com.vitalykalenik.aviatest.data.models.AviaResponse
import com.vitalykalenik.aviatest.domain.models.City
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Single
import org.junit.Test
import java.util.Locale

/**
 * Тест на [AviaRepositoryImpl]
 *
 * @author Vitaly Kalenik
 */
class AviaRepositoryImplTest {

    private val api = mockk<AviaApi>()
    private val repository = AviaRepositoryImpl(api)

    @Test
    fun `test get cities`(){
        val expected = AviaResponse(listOf(City()))
        every { api.getCities(any(), any()) } returns Single.just(expected)

        repository.getCities("Moscow").test().assertValue(expected.cities)
    }

    @Test
    fun `test get cities language`(){
        val expectedRu = AviaResponse(listOf(City(city = "Москва")))
        val expectedEn = AviaResponse(listOf(City(city = "Moscow")))
        every { api.getCities(any(), RU_LANG) } returns Single.just(expectedRu)
        every { api.getCities(any(), EN_LANG) } returns Single.just(expectedEn)

        Locale.setDefault(Locale(RU_LANG))
        repository.getCities("Moscow").test().assertValue(expectedRu.cities)

        Locale.setDefault(Locale(EN_LANG))
        repository.getCities("Moscow").test().assertValue(expectedEn.cities)

        Locale.setDefault(Locale(IT_LANG))
        repository.getCities("Moscow").test().assertValue(expectedEn.cities)
    }

    companion object {

        private const val RU_LANG = "ru"
        private const val EN_LANG = "en"
        private const val IT_LANG = "it"
    }
}