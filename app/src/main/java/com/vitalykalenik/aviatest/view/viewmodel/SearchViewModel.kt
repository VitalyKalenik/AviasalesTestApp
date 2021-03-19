package com.vitalykalenik.aviatest.view.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.vitalykalenik.aviatest.domain.SearchInteractor
import com.vitalykalenik.aviatest.domain.models.City
import com.vitalykalenik.aviatest.domain.models.OneWayConverter
import com.vitalykalenik.aviatest.utils.StringUtils
import com.vitalykalenik.aviatest.view.models.CityModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Observable
import io.reactivex.ObservableSource
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
    private val searchInteractor: SearchInteractor,
    private val converter: OneWayConverter<City, CityModel>
) : BaseViewModel() {

    private val searchSubject = PublishSubject.create<String>()

    private val searchSuccessLiveData = MutableLiveData<List<CityModel>>()
    private val searchEmptyQueryLiveData = MutableLiveData<Void>()
    private val searchFailureLiveData = MutableLiveData<Void>()

    /**
     * Лайвдата для событий успешного поиска
     */
    fun getSearchSuccessLiveData(): LiveData<List<CityModel>> = searchSuccessLiveData

    /**
     * Лайвдата для пустого запроса пользователя
     */
    fun getSearchEmptyQueryLiveData(): LiveData<Void> = searchEmptyQueryLiveData

    /**
     * Лайвдата для ошибок во время поиска
     */
    fun getSearchFailureLiveData(): LiveData<Void> = searchFailureLiveData

    init {
        createSearchSubject()
    }

    fun newQuery(query: String?) = searchSubject.onNext(query ?: StringUtils.EMPTY)

    private fun createSearchSubject() = searchSubject
        .debounce(REQUEST_INTERVAL, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
        .distinctUntilChanged()
        .filter { query ->
            if (query.isEmpty()) {
                searchEmptyQueryLiveData.setValue(null)
                false
            } else {
                true
            }
        }
        .switchMap { request ->
            searchInteractor.getCities(request)
                .toObservable()
                .subscribeOn(Schedulers.io())
                .onErrorResumeNext(ObservableSource {
                    searchFailureLiveData.postValue(null)
                    Observable.empty<List<City>>()
                })
        }
        .map { converter.convertList(it) }
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