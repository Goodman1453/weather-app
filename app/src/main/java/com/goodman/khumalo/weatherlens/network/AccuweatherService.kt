package com.goodman.khumalo.weatherlens.network

import com.goodman.khumalo.weatherlens.BuildConfig
import com.goodman.khumalo.weatherlens.model.LocationInfoResponse
import com.goodman.khumalo.weatherlens.model.Weather12HourForecastResponse
import com.goodman.khumalo.weatherlens.model.Weather5DayForecastResponse
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface



AccuweatherService {

    companion object {
        const val API_KEY = "FOeNJW3N5l0PSlKAMmwhEFdufEWlGoSr"
    }
    @GET("forecasts/v1/hourly/1hour/{key}")
    suspend fun getWeatherHourlyForecastAsync(@Path("key") key: String, @Query("apikey") apikey: String = API_KEY, @Query("details") details: String = "true", @Query("metric") metric: String = "true"): MutableList<Weather12HourForecastResponse>

    @GET("forecasts/v1/hourly/12hour/{key}")
    suspend  fun getWeather12HourlyForecast(@Path("key") key: String, @Query("apikey") apikey: String = API_KEY, @Query("details") details: String = "true", @Query("metric") metric: String = "true"): MutableList<Weather12HourForecastResponse>

    @GET("locations/v1/cities/geoposition/search")
    suspend fun getLocationDataAsync(@Query("apikey") apiKey: String = API_KEY, @Query("q") location: String): LocationInfoResponse

    @GET("forecasts/v1/daily/5day/{key}")
    suspend fun getWeather5DaysForecast(@Path("key") key: String, @Query("apikey") apikey: String = API_KEY, @Query("details") details: String = "true", @Query("metric") metric: String = "true"): Weather5DayForecastResponse
}