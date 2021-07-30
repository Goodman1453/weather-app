package com.goodman.khumalo.weatherlens.di

import com.goodman.khumalo.weatherlens.model.AccuWeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AccuweatherRepositoryModule {
    @Singleton
    @Provides
    fun provideAccuWeatherRepository(): AccuWeatherRepository {
        return AccuWeatherRepository()
    }
}