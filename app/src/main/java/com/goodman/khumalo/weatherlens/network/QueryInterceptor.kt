package com.goodman.khumalo.weatherlens.network

import android.provider.UserDictionary.Words.APP_ID
import com.goodman.khumalo.weatherlens.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class QueryInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val url = chain.request().url().newBuilder()
                .addQueryParameter("appid", "YhAESs3RWhRoAZQ7u89TiQZmLW7Q96sD")
                .build()

        val request = chain.request().newBuilder()
                .url(url)
                .build()

        return chain.proceed(request)
    }
}