package com.goodman.khumalo.weatherlens.network

import com.goodman.khumalo.weatherlens.BuildConfig
import com.goodman.khumalo.weatherlens.model.LocationInfoResponse
import com.goodman.khumalo.weatherlens.model.Weather12HourForecastResponse
import com.goodman.khumalo.weatherlens.model.Weather5DayForecastResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AccuApiInterface {

    companion object {
        const val API_KEY = "YhAESs3RWhRoAZQ7u89TiQZmLW7Q96sD"
    }
    @GET("forecasts/v1/hourly/1hour/{key}")
    fun getWeatherHourlyForecast(@Path("key") key: String, @Query("apikey") apikey: String = API_KEY, @Query("details") details: String = "true", @Query("metric") metric: String = "true"):  Call<MutableList<Weather12HourForecastResponse>>

    @GET("forecasts/v1/hourly/12hour/{key}")
     fun getWeather12HourlyForecast(@Path("key") key: String, @Query("apikey") apikey: String = API_KEY, @Query("details") details: String = "true", @Query("metric") metric: String = "true"): Call<MutableList<Weather12HourForecastResponse>>

    @GET("locations/v1/cities/geoposition/search")
     fun getLocationData(@Query("apikey") apiKey: String = API_KEY, @Query("q") location: String): Call<LocationInfoResponse>

    @GET("forecasts/v1/daily/5day/{key}")
     fun getWeather5DaysForecast(@Path("key") key: String, @Query("apikey") apikey: String = API_KEY, @Query("details") details: String = "true", @Query("metric") metric: String = "true"): Call<Weather5DayForecastResponse>
}