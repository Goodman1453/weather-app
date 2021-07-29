package com.goodman.khumalo.weatherlens.network

import com.google.gson.GsonBuilder
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {

    private var retrofit: Retrofit? = null
    private val BASE_URL = "http://dataservice.accuweather.com"

    private val client: Retrofit
        get() {
            if (retrofit == null) {
                synchronized(Retrofit::class.java) {

                     val client = OkHttpClient().newBuilder()
                        .addInterceptor(QueryInterceptor())
                        .connectTimeout(30, TimeUnit.SECONDS)
                        .retryOnConnectionFailure(true)
                        .readTimeout(30, TimeUnit.SECONDS)
                        .connectionPool(ConnectionPool(0, 1, TimeUnit.NANOSECONDS))
                        .build()

                        retrofit = Retrofit.Builder()
                                .baseUrl(BASE_URL)
                                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
                                .client(client)
                                .build()
                }

            }
            return retrofit!!
        }

    val accuWeatherApi = client.create(AccuweatherService::class.java)!!
}