package com.goodman.khumalo.weatherlens.viewmodel

import androidx.lifecycle.ViewModel
import com.goodman.khumalo.weatherlens.model.AccuWeatherModelImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccuWeatherViewModel @Inject constructor(private val accuWeatherModelImpl: AccuWeatherModelImpl) : ViewModel() {

    private val viewModelScope = CoroutineScope(SupervisorJob()+ Dispatchers.Main)
    val locationKey = accuWeatherModelImpl.locationInfoLiveData
    val hourlyForecast = accuWeatherModelImpl.hourlyForecastLiveData
    val fiveDayForecast = accuWeatherModelImpl.fiveDayForecastLiveData

    fun getLocationKey(coordinates: String){
        viewModelScope.launch {
            accuWeatherModelImpl.getLocationKey(coordinates)
        }
    }

    fun getHourlyForecast(locationKey: String){
        viewModelScope.launch {
            accuWeatherModelImpl.getWeatherHourlyForecast(locationKey)
        }
    }

    fun get5DailyForecast(locationKey: String){
        viewModelScope.launch {
            accuWeatherModelImpl.getWeather5dayForecast(locationKey)
        }
    }
}