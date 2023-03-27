package com.example.getweather.data.dataSource.cache

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.getweather.data.domain.WeatherInfo
import kotlin.math.roundToInt

const val WEATHER_KEY = 0

@Entity("weather_table")
data class WeatherCacheInfo (
    val temperature: Double,
    val location: String,
    @ColumnInfo("weather_description") val weatherDescription: String,
    val humidity: Double,
    @ColumnInfo("feels_like") val feelsLike: Double,
    @ColumnInfo("is_day") val isDay: String,
    @PrimaryKey(autoGenerate = false) val weatherKey : Int = WEATHER_KEY)

fun WeatherCacheInfo.toDomain() : WeatherInfo {
    return WeatherInfo(
        "${temperature.roundToInt()}°C",
        location,
        weatherDescription,
        "$humidity%",
        "${feelsLike.roundToInt()}°C",
        isDay
    )
}