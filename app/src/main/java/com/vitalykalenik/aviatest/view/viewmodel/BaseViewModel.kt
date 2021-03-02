package com.vitalykalenik.aviatest.view.viewmodel

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Базовый класс вьюмодели
 *
 * @author Vitaly Kalenik
 */
abstract class BaseViewModel : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    protected fun Disposable.addToCompositeDisposable() = compositeDisposable.add(this)

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}