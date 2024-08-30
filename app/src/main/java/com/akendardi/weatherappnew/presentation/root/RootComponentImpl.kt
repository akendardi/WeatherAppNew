package com.akendardi.weatherappnew.presentation.root

import com.arkivanov.decompose.ComponentContext

class RootComponentImpl(
    private val component: ComponentContext
) : RootComponent, ComponentContext by component