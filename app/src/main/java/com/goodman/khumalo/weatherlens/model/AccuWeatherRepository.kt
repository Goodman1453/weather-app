package com.goodman.khumalo.weatherlens.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.goodman.khumalo.weatherlens.network.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AccuWeatherRepository {

    private val _locationInfoLiveData = MutableLiveData<LocationInfoResponse>()
    val locationInfoLiveData: LiveData<LocationInfoResponse> = _locationInfoLiveData
    private val _progressBarLiveData = MutableLiveData<Boolean>()
    val progressBarLiveData: LiveData<Boolean> = _progressBarLiveData
    private val _hourlyForecastLiveData =
        MutableLiveData<MutableList<Weather12HourForecastResponse>>()
    val hourlyForecastLiveData: LiveData<MutableList<Weather12HourForecastResponse>> =
        _hourlyForecastLiveData
    private val _fiveDayForecastLiveData = MutableLiveData<Weather5DayForecastResponse>()
    val fiveDayForecastLiveData: LiveData<Weather5DayForecastResponse> = _fiveDayForecastLiveData
    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error


    suspend fun getLocationKey(coordinates: String) {
        withContext(Dispatchers.IO) {
            try {
                _progressBarLiveData.postValue(true)
                val response =
                    RetrofitClient.accuWeatherApi.getLocationDataAsync(location = coordinates)
                _locationInfoLiveData.postValue(response)
                _progressBarLiveData.postValue(false)
            } catch (e: Exception) {
                _progressBarLiveData.postValue(false)
                _error.postValue(e.message)
            }
        }
    }


    suspend fun getWeatherHourlyForecast(locationKey: String) {
        withContext(Dispatchers.IO) {
            try {
                _progressBarLiveData.postValue(true)
                val response =
                    RetrofitClient.accuWeatherApi.getWeatherHourlyForecastAsync(locationKey)
                _hourlyForecastLiveData.postValue(response)
                _progressBarLiveData.postValue(false)
            } catch (e: Exception) {
                _progressBarLiveData.postValue(false)
                _error.postValue(e.message)
            }
        }
    }

    suspend fun getWeather5dayForecast(locationKey: String) {
        withContext(Dispatchers.IO) {
            try {
                _progressBarLiveData.postValue(true)
                val response = RetrofitClient.accuWeatherApi.getWeather5DaysForecast(locationKey)
                _fiveDayForecastLiveData.postValue(response)
                _progressBarLiveData.postValue(false)
            } catch (e: Exception) {
                _progressBarLiveData.postValue(false)
                _error.postValue(e.message)
            }
        }

    }
}