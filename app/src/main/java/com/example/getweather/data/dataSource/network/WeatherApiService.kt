package com.example.getweather.data.dataSource.network

import com.example.getweather.data.dataSource.network.response.WeatherResponse
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

// Uncomment the line bellow and add your Weather Stack API-KEY
// const val API_KEY = "Your API KEY"
const val BASE_URL = "http://api.weatherstack.com/"


private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()

interface WeatherApiService {
    @GET("current")
    fun getWeatherInfo(
        @Query("access_key") key: String = API_KEY,
        @Query("query") city: String = "Uberaba"
    ) : Deferred<WeatherResponse>
}

object WeatherApi {
    val retrofitService: WeatherApiService by lazy {
        retrofit.create(WeatherApiService::class.java)
    }
}