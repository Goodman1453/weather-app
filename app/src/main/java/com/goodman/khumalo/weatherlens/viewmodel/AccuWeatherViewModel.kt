package com.goodman.khumalo.weatherlens.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.goodman.khumalo.weatherlens.model.*
import com.goodman.khumalo.weatherlens.utils.NetworkResults

class AccuWeatherViewModel: ViewModel() {
    val locationInfoLiveData = MutableLiveData<LocationInfoResponse>()
    val progressBarLiveData = MutableLiveData<Boolean>()
    val hourlyForecastLiveData = MutableLiveData<MutableList<Weather12HourForecastResponse>>()
    val fiveDayForecastLiveData = MutableLiveData<Weather5DayForecastResponse>()


    fun getLocationKey(coordinates: String ,model: AccuWeatherModel){
        progressBarLiveData.postValue(true)
        model.getLocationKey(coordinates , object : NetworkResults<LocationInfoResponse>{
            override fun onSuccess(data: LocationInfoResponse) {
                val locationKey = data
                progressBarLiveData.postValue(false)
                locationInfoLiveData.postValue(locationKey)
            }

            override fun onFailure(err: String) {
                progressBarLiveData.postValue(false)
            }

        })
    }

    fun getHourlyForecast(locationKey: String, model: AccuWeatherModel){
        progressBarLiveData.postValue(true)
        model.getWeatherHourlyForecast(locationKey, object : NetworkResults<MutableList<Weather12HourForecastResponse>>{
            override fun onSuccess(data: MutableList<Weather12HourForecastResponse>) {
                progressBarLiveData.postValue(false)
                val hourlyForecast: MutableList<Weather12HourForecastResponse> = data
                hourlyForecastLiveData.postValue(hourlyForecast)
            }

            override fun onFailure(err: String) {
               progressBarLiveData.postValue(false)
            }

        })
    }

    fun get5DailyForecast(locationKey: String, model: AccuWeatherModel){
        progressBarLiveData.postValue(true)
        model.getWeather5dayForecast(locationKey, object : NetworkResults<Weather5DayForecastResponse>{
            override fun onSuccess(data: Weather5DayForecastResponse) {
                progressBarLiveData.postValue(false)
                val dailyForecastList: Weather5DayForecastResponse = data
                fiveDayForecastLiveData.postValue(dailyForecastList)
            }

            override fun onFailure(err: String) {
                progressBarLiveData.postValue(false)
            }

        })
    }
}