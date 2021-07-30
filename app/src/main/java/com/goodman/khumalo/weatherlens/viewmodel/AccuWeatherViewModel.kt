package com.goodman.khumalo.weatherlens.viewmodel

import androidx.lifecycle.ViewModel
import com.goodman.khumalo.weatherlens.model.AccuWeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccuWeatherViewModel @Inject constructor(private val accuWeatherRepository: AccuWeatherRepository) : ViewModel() {

    private val viewModelScope = CoroutineScope(SupervisorJob()+ Dispatchers.Main)
    val locationKey = accuWeatherRepository.locationInfoLiveData
    val hourlyForecast = accuWeatherRepository.hourlyForecastLiveData
    val fiveDayForecast = accuWeatherRepository.fiveDayForecastLiveData

    fun getLocationKey(coordinates: String){
        viewModelScope.launch {
            accuWeatherRepository.getLocationKey(coordinates)
        }
    }

    fun getHourlyForecast(locationKey: String){
        viewModelScope.launch {
            accuWeatherRepository.getWeatherHourlyForecast(locationKey)
        }
    }

    fun get5DailyForecast(locationKey: String){
        viewModelScope.launch {
            accuWeatherRepository.getWeather5dayForecast(locationKey)
        }
    }
}