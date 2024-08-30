package com.akendardi.weatherappnew.data.mapper

import com.akendardi.weatherappnew.data.local.model.CityDbModel
import com.akendardi.weatherappnew.domain.entity.City

fun City.toDbModel(): CityDbModel = CityDbModel(id, name, country)

fun CityDbModel.toEntity(): City = City(id, name, country)

fun List<CityDbModel>.toEntities(): List<City> = map{it.toEntity()}
