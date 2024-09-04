package com.akendardi.weatherappnew.presentation.root

import androidx.compose.runtime.Composable
import com.akendardi.weatherappnew.presentation.details.DetailsContent
import com.akendardi.weatherappnew.presentation.favourite.FavouriteContent
import com.akendardi.weatherappnew.presentation.search.SearchContent
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children

@Composable
fun RootContent(component: RootComponent){

    Children(component.stack) {
        when(val instance = it.instance){
            is RootComponent.Child.Details -> {
                DetailsContent(instance.component)
            }

            is RootComponent.Child.Favourite -> {
                FavouriteContent(instance.component)
            }

            is RootComponent.Child.Search -> {
                SearchContent(instance.component)
            }

        }
    }
}