package com.vitalykalenik.aviatest.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.vitalykalenik.aviatest.data.models.AviaResponse
import com.vitalykalenik.aviatest.domain.SearchInteractor
import com.vitalykalenik.aviatest.domain.models.City
import com.vitalykalenik.aviatest.utils.StringUtils
import com.vitalykalenik.aviatest.view.models.CityModel
import com.vitalykalenik.aviatest.view.models.CityToCityModelConverter
import com.vitalykalenik.aviatest.view.viewmodel.SearchViewModel
import io.mockk.called
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Тест на [SearchViewModel]
 *
 * @author Vitaly Kalenik
 */
class SearchViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val interactor = mockk<SearchInteractor>()
    private val converter = CityToCityModelConverter()
    private lateinit var viewModel: SearchViewModel

    @Before
    fun `set up`() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        viewModel = SearchViewModel(interactor, converter)
    }

    @Test
    fun `test search success`() {
        val successLiveData = mockk<Observer<List<CityModel>>>()
        val expected = listOf(City())
        every { interactor.getCities(any()) } returns Single.just(expected)
        viewModel.getSearchSuccessLiveData().observeForever(successLiveData)
        viewModel.newQuery("Moscow")
        verify {
            successLiveData.onChanged(converter.convertList(expected))
        }
    }

    @Test
    fun `test search empty`() {
        val successLiveData = mockk<Observer<List<CityModel>>>()
        val expected = listOf(City())
        every { interactor.getCities(any()) } returns Single.just(expected)
        viewModel.getSearchSuccessLiveData().observeForever(successLiveData)
        viewModel.newQuery(StringUtils.EMPTY)
        verify {
            successLiveData.onChanged(emptyList())
        }
    }

    @Test
    fun `test search repeat`() {
        val successLiveData = mockk<Observer<List<CityModel>>>()
        val expected = listOf(City())
        every { interactor.getCities(any()) } returns Single.just(expected)
        viewModel.getSearchSuccessLiveData().observeForever(successLiveData)
        viewModel.newQuery("Moscow")
        viewModel.newQuery("Moscow")
        verify(exactly = 1) {
            successLiveData.onChanged(converter.convertList(expected))
        }
    }

    @Test
    fun `test search failure`() {
        val failureLiveData = mockk<Observer<Void>>()
        every { interactor.getCities(any()) } returns Single.error(Exception())
        viewModel.getSearchFailureLiveData().observeForever(failureLiveData)
        viewModel.newQuery("Moscow")
        verify {
            failureLiveData.onChanged(null)
        }
    }
}