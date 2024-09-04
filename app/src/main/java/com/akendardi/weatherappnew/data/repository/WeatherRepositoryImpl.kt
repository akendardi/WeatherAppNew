package com.akendardi.weatherappnew.data.repository

import android.util.Log
import com.akendardi.weatherappnew.data.mapper.toEntity
import com.akendardi.weatherappnew.data.network.api.ApiService
import com.akendardi.weatherappnew.domain.entity.Forecast
import com.akendardi.weatherappnew.domain.entity.Weather
import com.akendardi.weatherappnew.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : WeatherRepository {
    override suspend fun getWeather(cityId: Int): Weather {
        return apiService.loadCurrentWeather("$PREFIX_CITY_ID$cityId").toEntity()
    }

    override suspend fun getForecast(cityId: Int): Forecast {
        return apiService.loadForecast("$PREFIX_CITY_ID$cityId").toEntity()
    }

    private companion object {
        private const val PREFIX_CITY_ID = "id:"
    }
}