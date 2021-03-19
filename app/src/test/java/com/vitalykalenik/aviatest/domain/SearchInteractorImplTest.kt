package com.vitalykalenik.aviatest.domain

import com.vitalykalenik.aviatest.domain.models.City
import com.vitalykalenik.aviatest.data.models.AviaResponse
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Single
import org.junit.Test

/**
 * Тест на [SearchInteractorImpl]
 *
 * @author Vitaly Kalenik
 */
class SearchInteractorImplTest {

    private val repository = mockk<AviaRepository>()
    private val interactor = SearchInteractorImpl(repository)

    @Test
    fun `test search`(){
        val expected = listOf(City())
        every { repository.getCities(any()) } returns Single.just(expected)
        interactor.getCities("Moscow").test().assertValue(expected)
    }
}