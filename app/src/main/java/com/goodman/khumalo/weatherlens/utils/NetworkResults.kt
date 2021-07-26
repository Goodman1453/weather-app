package com.goodman.khumalo.weatherlens.utils

interface NetworkResults<T> {
    fun onSuccess(data: T)
    fun onFailure(err: String)
}