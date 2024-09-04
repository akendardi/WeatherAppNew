package com.akendardi.weatherappnew.data.mapper

import com.akendardi.weatherappnew.data.network.dto.CityDto
import com.akendardi.weatherappnew.domain.entity.City

fun CityDto.toEntity(): City = City(id, name, country)

fun List<CityDto>.toEntities(): List<City> = map{it.toEntity()}