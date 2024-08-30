package com.akendardi.weatherappnew.presentation.details

import com.arkivanov.decompose.ComponentContext

class DetailsComponentImpl(
    private val component: ComponentContext
) : DetailsComponent, ComponentContext by component