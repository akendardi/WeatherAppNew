package com.akendardi.weatherappnew.domain.entity

data class Forecast(
    val currentWeather: Weather,
    val upcoming: List<Weather>
)
