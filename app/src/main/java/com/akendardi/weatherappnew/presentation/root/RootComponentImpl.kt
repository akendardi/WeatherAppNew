package com.akendardi.weatherappnew.presentation.root

import com.akendardi.weatherappnew.domain.entity.City
import com.akendardi.weatherappnew.presentation.details.DetailsComponentImpl
import com.akendardi.weatherappnew.presentation.favourite.FavouriteComponentImpl
import com.akendardi.weatherappnew.presentation.search.OpenReason
import com.akendardi.weatherappnew.presentation.search.SearchComponentImpl
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.parcelize.Parcelize

class RootComponentImpl @AssistedInject constructor(
    @Assisted("component") component: ComponentContext,
    private val detailsComponentFactory: DetailsComponentImpl.Factory,
    private val searchComponentFactory: SearchComponentImpl.Factory,
    private val favouriteComponentFactory: FavouriteComponentImpl.Factory

) : RootComponent, ComponentContext by component {

    private val navigation = StackNavigation<Config>()
    override val stack: Value<ChildStack<*, RootComponent.Child>> = childStack(
        source = navigation,
        initialConfiguration = Config.Favourite,
        handleBackButton = true,
        childFactory = ::child
    )

    private fun child(
        config: Config,
        component: ComponentContext
    ): RootComponent.Child {
        when (config) {
            is Config.Details -> {
                val component = detailsComponentFactory.create(
                    city = config.city,
                    onBackClicked = {
                        navigation.pop()
                    },
                    component = component
                )
                return RootComponent.Child.Details(component)
            }

            Config.Favourite -> {
                val component = favouriteComponentFactory.create(
                    component = component,
                    onCityItemClicked = {
                        navigation.push(Config.Details(it))
                    },
                    onSearchClick = {
                        navigation.push(Config.Search(OpenReason.RegularOpen))
                    },
                    onAddFavouriteClick = {
                        navigation.push(Config.Search(OpenReason.Add))
                    }
                )
                return RootComponent.Child.Favourite(component)
            }

            is Config.Search -> {
                val component = searchComponentFactory.create(
                    openReason = config.openReason,
                    onBackClicked = {
                        navigation.pop()
                    },
                    onCitySavedToFavourite = {
                        navigation.pop()
                    },
                    onForecastForCityRequested = {
                        navigation.push(Config.Details(it))
                    },
                    component = component
                )
                return RootComponent.Child.Search(component)
            }

        }
    }

    sealed interface Config : Parcelable {
        @Parcelize
        data object Favourite : Config

        @Parcelize
        data class Details(val city: City) : Config

        @Parcelize
        data class Search(val openReason: OpenReason) : Config
    }

    @AssistedFactory
    interface Factory {
        fun create(@Assisted("component") component: ComponentContext): RootComponentImpl
    }
}