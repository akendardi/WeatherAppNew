package com.akendardi.weatherappnew.data.repository

import com.akendardi.weatherappnew.data.mapper.toEntities
import com.akendardi.weatherappnew.data.network.api.ApiService
import com.akendardi.weatherappnew.domain.entity.City
import com.akendardi.weatherappnew.domain.repository.SearchRepository
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : SearchRepository {
    override suspend fun search(query: String): List<City> {
        return apiService.searchCity(query).toEntities()
    }
}