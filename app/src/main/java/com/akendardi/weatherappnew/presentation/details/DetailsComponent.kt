package com.akendardi.weatherappnew.presentation.details

import kotlinx.coroutines.flow.StateFlow

interface DetailsComponent {

    val model: StateFlow<DetailsStore.State>

    fun onClickChangeFavourite()

    fun onClickBack()
}