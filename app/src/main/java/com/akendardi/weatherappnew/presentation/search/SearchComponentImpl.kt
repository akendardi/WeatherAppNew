package com.akendardi.weatherappnew.presentation.search

import com.arkivanov.decompose.ComponentContext

class SearchComponentImpl(
    private val component: ComponentContext
) : SearchComponent, ComponentContext by component