package com.akendardi.weatherappnew.presentation.root

import com.akendardi.weatherappnew.presentation.details.DetailsComponent
import com.akendardi.weatherappnew.presentation.favourite.FavouriteComponent
import com.akendardi.weatherappnew.presentation.search.SearchComponent
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value

interface RootComponent {

    val stack: Value<ChildStack<*, Child>>

    sealed interface Child {

        data class Favourite(val component: FavouriteComponent): Child
        data class Search(val component: SearchComponent): Child
        data class Details(val component: DetailsComponent): Child
    }
}