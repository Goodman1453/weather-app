package com.goodman.khumalo.weatherlens.utils

import java.text.SimpleDateFormat
import java.util.*

object DateFormatter {
    private val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZ", Locale.ENGLISH)

    fun changeDateFormatToDate(dateTime: String): String {
        val outputFormat = SimpleDateFormat("MMM dd", Locale.ENGLISH)
        val date: Date = inputFormat.parse(dateTime)
        return outputFormat.format(date)
    }
}