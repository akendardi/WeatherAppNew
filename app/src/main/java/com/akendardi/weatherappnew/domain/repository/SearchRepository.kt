package com.akendardi.weatherappnew.domain.repository

import com.akendardi.weatherappnew.domain.entity.City

interface SearchRepository {

    suspend fun search(query: String): List<City>
}