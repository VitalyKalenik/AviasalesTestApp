package com.vitalykalenik.aviatest.domain

import com.vitalykalenik.aviatest.domain.models.City
import io.reactivex.Single
import javax.inject.Inject

/**
 * Реализация [SearchInteractor]
 *
 * @property aviaRepository Репозиторий
 *
 * @author Vitaly Kalenik
 */
class SearchInteractorImpl @Inject constructor(
    private val aviaRepository: AviaRepository
) : SearchInteractor {

    override fun getCities(request: String): Single<List<City>> = aviaRepository.getCities(request)
}