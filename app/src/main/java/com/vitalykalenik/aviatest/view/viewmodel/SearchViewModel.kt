package com.vitalykalenik.aviatest.view.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.vitalykalenik.aviatest.domain.SearchInteractor
import com.vitalykalenik.aviatest.models.AviaResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Вьюмодель для экрана со строкой поиска
 *
 * @property searchInteractor Интерактор для поиска городов
 *
 * @author Vitaly Kalenik
 */
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchInteractor: SearchInteractor
) : BaseViewModel() {

    private val searchSuccessLiveData = MutableLiveData<AviaResponse>()
    private val searchFailureLiveData = MutableLiveData<Void>()

    /**
     * Подписка на новые поисковые запросы
     */
    val searchSubject = PublishSubject.create<String>()

    /**
     * Лайвдата для событий успешного поиска
     */
    fun getSearchSuccessLiveData(): LiveData<AviaResponse> = searchSuccessLiveData

    /**
     * Лайвдата для ошибок во время поиска
     */
    fun getSearchFailureLiveData(): LiveData<Void> = searchFailureLiveData

    init {
        createSearchSubject()
    }

    private fun createSearchSubject() = searchSubject
        .debounce(REQUEST_INTERVAL, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
        .distinctUntilChanged()
        .switchMap {
            if (it.isEmpty()) {
                Observable.just(AviaResponse(emptyList()))
            } else {
                searchInteractor.getCities(it)
                    .toObservable()
                    .subscribeOn(Schedulers.io())
            }
        }
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
            {
                searchSuccessLiveData.setValue(it)
            }, {
                searchFailureLiveData.setValue(null)
            }
        ).addToCompositeDisposable()

    companion object {

        private const val REQUEST_INTERVAL = 500L
    }
}