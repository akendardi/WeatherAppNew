package com.akendardi.weatherappnew.data.repository

import com.akendardi.weatherappnew.data.local.db.FavouriteCitiesDao
import com.akendardi.weatherappnew.data.mapper.toDbModel
import com.akendardi.weatherappnew.data.mapper.toEntities
import com.akendardi.weatherappnew.domain.entity.City
import com.akendardi.weatherappnew.domain.repository.FavouriteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavouriteRepositoryImpl @Inject constructor(
    private val favouriteCitiesDao: FavouriteCitiesDao
) : FavouriteRepository {

    override val favouriteCity: Flow<List<City>> = favouriteCitiesDao.getFavouriteCities()
        .map { it.toEntities() }

    override fun observeIsFavourite(cityId: Int): Flow<Boolean> =
        favouriteCitiesDao.observeIsFavourite(cityId)

    override suspend fun addFavouriteCity(city: City) {
        favouriteCitiesDao.addToFavourite(city.toDbModel())
    }

    override suspend fun removeFavouriteCity(cityId: Int) {
        favouriteCitiesDao.removeFromFavourite(cityId)
    }
}