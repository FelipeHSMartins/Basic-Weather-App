package com.example.getweather.ui.mainScreen

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.getweather.data.domain.WeatherInfo
import com.example.getweather.data.repositories.WeatherRepository
import com.example.getweather.onlyLetters
import kotlinx.coroutines.*

class MainViewModel(application: Application, private val repository: WeatherRepository): ViewModel() {
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _weatherInfo = repository.weatherInfo as MutableLiveData<WeatherInfo>

    val weatherInfo: LiveData<WeatherInfo>
        get() = _weatherInfo

    init {
        refreshWeatherInfo()
    }


    fun changeCity(newCity: String) = coroutineScope.launch {
        repository.changeCity(newCity)
    }

    fun cityInputIsValid(input: String): Boolean {
        return (input.isNotBlank() && input.onlyLetters())
    }

    private fun refreshWeatherInfo() = coroutineScope.launch {
        repository.upsertWeather()
        _weatherInfo.postValue(repository.weatherInfo.value)
    }

    override fun onCleared() {
        super.onCleared()
        coroutineScope.cancel()
    }

    // Function and Variable responsible for keeping track of the CHANGE CITY OPTION status
    var changeCityOptionShowing = false
    fun updateCityOptionStatus() {
        changeCityOptionShowing = changeCityOptionShowing != true
    }
}

class Factory(private val app: Application, private val repository: WeatherRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(app, repository) as T
        }
        throw IllegalArgumentException("Unable to construct viewModel")
    }
}