package com.akendardi.weatherappnew.domain.repository

import com.akendardi.weatherappnew.domain.entity.Forecast
import com.akendardi.weatherappnew.domain.entity.Weather

interface WeatherRepository {

    suspend fun getWeather(cityId: Int): Weather

    suspend fun getForecast(cityId: Int): Forecast
}