package com.akendardi.weatherappnew.domain.usecase

import com.akendardi.weatherappnew.domain.repository.SearchRepository
import javax.inject.Inject

class SearchCityUseCase @Inject constructor(
    private val repository: SearchRepository
) {
    suspend operator fun invoke(query: String) = repository.search(query)

}