package com.akendardi.weatherappnew.presentation.favourite

import com.arkivanov.decompose.ComponentContext

class FavouriteComponentImpl(
    private val component: ComponentContext
) : FavouriteComponent, ComponentContext by component