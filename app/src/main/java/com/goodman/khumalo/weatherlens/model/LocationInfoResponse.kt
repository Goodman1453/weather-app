package com.goodman.khumalo.weatherlens.model

import com.google.gson.annotations.SerializedName

data class LocationInfoResponse(@SerializedName("Key") val key: String ?= "Key", @SerializedName("Type") val type: String ?= "", @SerializedName("LocalizedName") val localizedName: String ?= "", @SerializedName("EnglishName") val englishName: String ?= "")
