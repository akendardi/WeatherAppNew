package com.akendardi.weatherappnew.domain.usecase

import com.akendardi.weatherappnew.domain.repository.FavouriteRepository
import javax.inject.Inject

class GetFavouriteCitiesUseCase @Inject constructor(
    private val repository: FavouriteRepository
) {
    operator fun invoke() = repository.favouriteCity
}