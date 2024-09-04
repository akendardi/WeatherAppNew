package com.akendardi.weatherappnew.domain.repository

import com.akendardi.weatherappnew.domain.entity.City
import kotlinx.coroutines.flow.Flow

interface FavouriteRepository {

    val favouriteCity: Flow<List<City>>

    fun observeIsFavourite(cityId: Int): Flow<Boolean>

    suspend fun addFavouriteCity(city: City)

    suspend fun removeFavouriteCity(cityId: Int)
}