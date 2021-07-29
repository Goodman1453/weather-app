package com.goodman.khumalo.weatherlens.di

import com.goodman.khumalo.weatherlens.model.AccuWeatherModelImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class ImplementationModule {
    @Singleton
    @Provides
    fun provideAccuWeatherImpl(): AccuWeatherModelImpl {
        return AccuWeatherModelImpl()
    }
}