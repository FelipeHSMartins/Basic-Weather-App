package com.example.getweather.data.dataSource.network.response

import com.example.getweather.data.dataSource.cache.WeatherCacheInfo
import com.google.gson.annotations.SerializedName


data class WeatherResponse(
    @SerializedName("current")
    val weatherApiInfo: WeatherApiInfo,
    val location: Location,
    val request: Request
)

fun WeatherResponse.toWeatherCacheInfo() : WeatherCacheInfo {
    val weatherInfo = weatherApiInfo
    val weatherLocation = location
    return WeatherCacheInfo(
        weatherInfo.temperature,
        "${location.name}, ${location.region}",
        weatherInfo.weatherDescriptions[0],
        weatherInfo.humidity,
        weatherInfo.feelslike,
        weatherInfo.isDay
    )
}