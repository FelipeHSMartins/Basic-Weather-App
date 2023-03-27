package com.example.getweather.data.dataSource.cache

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(weatherCacheInfo: WeatherCacheInfo)

    @Query("SELECT * FROM weather_table WHERE weatherKey = $WEATHER_KEY")
    fun getCacheWeatherInfo() : LiveData<WeatherCacheInfo>

    @Query("SELECT location FROM weather_table WHERE weatherKey = $WEATHER_KEY")
    fun getCurrentCity(): String

}