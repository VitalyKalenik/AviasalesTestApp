package com.vitalykalenik.aviatest.di

import com.vitalykalenik.aviatest.data.AviaRepositoryImpl
import com.vitalykalenik.aviatest.data.api.AviaApi
import com.vitalykalenik.aviatest.domain.AviaRepository
import com.vitalykalenik.aviatest.domain.SearchInteractor
import com.vitalykalenik.aviatest.domain.SearchInteractorImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Модуль для провайда зависимостей
 *
 * @author Vitaly Kalenik
 */
@Module
@InstallIn(SingletonComponent::class)
object HiltModule {

    @Singleton
    @Provides
    fun provideApi() : AviaApi = Retrofit.Builder()
        .baseUrl(AviaApi.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(AviaApi::class.java)

    @Singleton
    @Provides
    fun provideRepository(api: AviaApi) : AviaRepository = AviaRepositoryImpl(api)

    @Provides
    fun provideSearchInteractor(repository: AviaRepository) : SearchInteractor = SearchInteractorImpl(repository)
}