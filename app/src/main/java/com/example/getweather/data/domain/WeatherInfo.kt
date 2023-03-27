package com.example.getweather.data.domain

data class WeatherInfo (
    val temperature: String,
    val location: String,
    val weatherDescription: String,
    val humidity: String,
    val feelsLike: String,
    val isDay: String)