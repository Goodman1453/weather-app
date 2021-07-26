package com.goodman.khumalo.weatherlens.network

import com.goodman.khumalo.weatherlens.network.AccuApiInterface.Companion.API_KEY
import okhttp3.Interceptor
import okhttp3.Response

class QueryInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val url = chain.request().url().newBuilder()
                .addQueryParameter("appid", API_KEY)
                .build()

        val request = chain.request().newBuilder()
                .url(url)
                .build()

        return chain.proceed(request)
    }
}