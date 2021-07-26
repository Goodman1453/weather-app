package com.goodman.khumalo.weatherlens.model

import com.goodman.khumalo.weatherlens.utils.NetworkResults

interface AccuWeatherModel {

    fun getLocationKey(coordinates: String, callback: NetworkResults<LocationInfoResponse>)

    fun getWeatherHourlyForecast(locationKey: String, callback: NetworkResults<MutableList<Weather12HourForecastResponse>>)

    fun getWeather5dayForecast(locationKey: String, callback: NetworkResults<Weather5DayForecastResponse>)
}