package com.akendardi.weatherappnew.presentation.favourite

import com.akendardi.weatherappnew.domain.entity.City
import com.akendardi.weatherappnew.presentation.componentScope
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavouriteComponentImpl @Inject constructor(
    private val storeFactory: FavouriteStoreFactory,
    private val component: ComponentContext,
    private val onCityItemClick: (City) -> Unit,
    private val onSearchClick: () -> Unit,
    private val onAddFavouriteClick: () -> Unit
) : FavouriteComponent, ComponentContext by component {

    private val scope = componentScope()

    private val store = instanceKeeper.getStore {
        storeFactory.create()
    }

    init {
        scope.launch {
            store.labels.collect {
                when (it) {
                    is FavouriteStore.Label.CityItemClick -> {
                        onCityItemClick(it.city)
                    }

                    FavouriteStore.Label.ClickSearch -> {
                        onSearchClick()
                    }

                    FavouriteStore.Label.ClickToFavourite -> {
                        onAddFavouriteClick()
                    }

                }
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override val model: StateFlow<FavouriteStore.State> = store.stateFlow

    override fun onClickSearch() {
        store.accept(FavouriteStore.Intent.ClickSearch)
    }

    override fun onClickAddFavourite() {
        store.accept(FavouriteStore.Intent.ClickToAddFavourite)
    }

    override fun onCityItemClick(city: City) {
        store.accept(FavouriteStore.Intent.CityItemClick(city))
    }
}