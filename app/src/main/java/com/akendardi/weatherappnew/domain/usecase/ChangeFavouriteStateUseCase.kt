package com.akendardi.weatherappnew.domain.usecase

import com.akendardi.weatherappnew.domain.entity.City
import com.akendardi.weatherappnew.domain.repository.FavouriteRepository
import javax.inject.Inject

class ChangeFavouriteStateUseCase @Inject constructor(
    private val repository: FavouriteRepository
) {
    suspend fun addToFavourite(city: City) = repository.addFavouriteCity(city)

    suspend fun removeFromFavourite(cityId: Int) = repository.removeFavouriteCity(cityId)

}