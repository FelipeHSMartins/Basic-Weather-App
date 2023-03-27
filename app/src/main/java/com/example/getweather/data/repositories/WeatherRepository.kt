package com.example.getweather.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.getweather.data.dataSource.cache.WeatherDatabase
import com.example.getweather.data.dataSource.cache.toDomain
import com.example.getweather.data.dataSource.network.WeatherApi
import com.example.getweather.data.dataSource.network.response.toWeatherCacheInfo
import com.example.getweather.data.domain.WeatherInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WeatherRepository (private val database: WeatherDatabase) {

    val weatherInfo: LiveData<WeatherInfo> = Transformations.map(database.weatherDao.getCacheWeatherInfo()) {
        it.toDomain()
    }


    suspend fun changeCity(newCity: String) {
        withContext(Dispatchers.IO) {
            val weatherInfo = WeatherApi.retrofitService.getWeatherInfo(city = newCity).await()
            database.weatherDao.upsert(weatherInfo.toWeatherCacheInfo())
        }
    }

    suspend fun upsertWeather() {
        withContext(Dispatchers.IO) {
            val currentCity = database.weatherDao.getCurrentCity()
            val weatherInfo = WeatherApi.retrofitService.getWeatherInfo(city = currentCity).await()
            database.weatherDao.upsert(weatherInfo.toWeatherCacheInfo())
        }
    }

}