package com.vitalykalenik.aviatest.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateHandle
import com.vitalykalenik.aviatest.domain.SearchInteractor
import com.vitalykalenik.aviatest.models.AviaResponse
import com.vitalykalenik.aviatest.models.City
import com.vitalykalenik.aviatest.utils.StringUtils
import com.vitalykalenik.aviatest.view.viewmodel.SearchViewModel
import io.mockk.called
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import io.reactivex.schedulers.TestScheduler
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.TimeUnit

/**
 * Тест на [SearchViewModel]
 *
 * @author Vitaly Kalenik
 */
class SearchViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val interactor = mockk<SearchInteractor>()
    private lateinit var viewModel: SearchViewModel

    @Before
    fun `set up`(){
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        viewModel = SearchViewModel(interactor)
    }

    @Test
    fun `test search success`(){
        val successLiveData = mockk<Observer<AviaResponse>>()
        val expected = AviaResponse(listOf(City()))
        every { interactor.getCities(any()) } returns Single.just(expected)
        viewModel.getSearchSuccessLiveData().observeForever(successLiveData)
        viewModel.searchSubject.onNext("Moscow")
        verify {
            successLiveData.onChanged(expected)
        }
    }

    @Test
    fun `test search empty`(){
        val successLiveData = mockk<Observer<AviaResponse>>()
        val expected = AviaResponse(listOf(City()))
        every { interactor.getCities(any()) } returns Single.just(expected)
        viewModel.getSearchSuccessLiveData().observeForever(successLiveData)
        viewModel.searchSubject.onNext(StringUtils.EMPTY)
        verify {
            successLiveData wasNot called
        }
    }

    @Test
    fun `test search repeat`(){
        val successLiveData = mockk<Observer<AviaResponse>>()
        val expected = AviaResponse(listOf(City()))
        every { interactor.getCities(any()) } returns Single.just(expected)
        viewModel.getSearchSuccessLiveData().observeForever(successLiveData)
        viewModel.searchSubject.onNext("Moscow")
        viewModel.searchSubject.onNext("Moscow")
        verify(exactly = 1) {
            successLiveData.onChanged(expected)
        }
    }

    @Test
    fun `test search failure`(){
        val failureLiveData = mockk<Observer<Void>>()
        every { interactor.getCities(any()) } returns Single.error(Exception())
        viewModel.getSearchFailureLiveData().observeForever(failureLiveData)
        viewModel.searchSubject.onNext("Moscow")
        verify {
            failureLiveData.onChanged(null)
        }
    }
}