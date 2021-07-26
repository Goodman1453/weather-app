package com.goodman.khumalo.weatherlens.model

import com.google.gson.annotations.SerializedName

data class Weather5DayForecastResponse(@SerializedName("DailyForecasts") val dailyForeCasts: List<DayForecast>)

data class DayForecast(@SerializedName("Date") val date: String, @SerializedName("Sun") val sun: Sun,
                       @SerializedName("Temperature") val temperature: DayTemperature, @SerializedName("Day") val day: Day,
                       @SerializedName("Night") val night: Day, @SerializedName("MobileLink") val mobileLink: String)

data class Sun(@SerializedName("Rise") val rise: String, @SerializedName("Set") val set: String)

data class DayTemperature(@SerializedName("Maximum") val maximum: Maximum, @SerializedName("Minimum") val minimum: Maximum)

data class Maximum(@SerializedName("Value") val value: Double, @SerializedName("Unit") val unit: String)

data class Day(@SerializedName("Icon") val icon: Int)
