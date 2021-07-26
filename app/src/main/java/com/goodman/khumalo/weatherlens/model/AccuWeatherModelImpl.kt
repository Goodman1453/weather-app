package com.goodman.khumalo.weatherlens.model

import android.content.Context
import com.goodman.khumalo.weatherlens.network.AccuApiInterface
import com.goodman.khumalo.weatherlens.network.RetrofitClient
import com.goodman.khumalo.weatherlens.utils.NetworkResults
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AccuWeatherModelImpl: AccuWeatherModel {

    override fun getLocationKey(coordinates: String ,callback: NetworkResults<LocationInfoResponse>) {
        val accuApiInterface: AccuApiInterface = RetrofitClient.client.create(AccuApiInterface::class.java)
        val call: Call<LocationInfoResponse> = accuApiInterface.getLocationData(location = coordinates)

        call.enqueue(object : Callback<LocationInfoResponse>{
            override fun onResponse(call: Call<LocationInfoResponse>, response: Response<LocationInfoResponse>) {
                if (response.body() != null)
                    callback.onSuccess(response.body()!!)
                else
                    callback.onFailure(response.message())
            }

            override fun onFailure(call: Call<LocationInfoResponse>, t: Throwable) {
                callback.onFailure(t.localizedMessage)
            }
        })
    }


    override fun getWeatherHourlyForecast(
        locationKey: String,
        callback: NetworkResults<MutableList<Weather12HourForecastResponse>>
    ) {
        val accuApiInterface: AccuApiInterface = RetrofitClient.client.create(AccuApiInterface::class.java)
        val call : Call<MutableList<Weather12HourForecastResponse>> = accuApiInterface.getWeatherHourlyForecast(locationKey)

        call.enqueue(object : Callback<MutableList<Weather12HourForecastResponse>>{
            override fun onResponse(
                call: Call<MutableList<Weather12HourForecastResponse>>,
                response: Response<MutableList<Weather12HourForecastResponse>>
            ) {
                if (response.body() != null)
                    callback.onSuccess(response.body()!!)
                else
                    callback.onFailure(response.message())
            }

            override fun onFailure(call: Call<MutableList<Weather12HourForecastResponse>>, t: Throwable) {
                callback.onFailure(t.localizedMessage)
            }

        })
    }

    override fun getWeather5dayForecast(
        locationKey: String,
        callback: NetworkResults<Weather5DayForecastResponse>
    ) {
        val accuApiInterface: AccuApiInterface = RetrofitClient.client.create(AccuApiInterface::class.java)
        val call : Call<Weather5DayForecastResponse> = accuApiInterface.getWeather5DaysForecast(locationKey)

        call.enqueue(object : Callback<Weather5DayForecastResponse>{
            override fun onResponse(
                call: Call<Weather5DayForecastResponse>,
                response: Response<Weather5DayForecastResponse>
            ) {
                if (response.body() != null)
                    callback.onSuccess(response.body()!!)
                else
                    callback.onFailure(response.message())
            }

            override fun onFailure(call: Call<Weather5DayForecastResponse>, t: Throwable) {
                callback.onFailure(t.localizedMessage)
            }

        })
    }


}