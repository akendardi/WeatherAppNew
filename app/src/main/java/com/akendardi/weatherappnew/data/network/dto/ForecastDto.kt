package com.akendardi.weatherappnew.data.network.dto

import com.google.gson.annotations.SerializedName

data class ForecastDto(
    @SerializedName("forecast") val forecastDay: List<DayDto>
)
