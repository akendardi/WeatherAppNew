package com.akendardi.weatherappnew.presentation.details

import com.akendardi.weatherappnew.domain.entity.City
import com.akendardi.weatherappnew.presentation.componentScope
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailsComponentImpl(
    private val city: City,
    private val storeFactory: DetailsStoreFactory,
    private val component: ComponentContext,
    private val onBackClicked: () -> Unit
) : DetailsComponent, ComponentContext by component{

    private val scope = componentScope()

    private val store = instanceKeeper.getStore {
        storeFactory.create(city)
    }

    init {
        scope.launch {
            store.labels.collect{
                when(it){
                    DetailsStore.Label.ClickBack -> {
                        onBackClicked()
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override val model: StateFlow<DetailsStore.State> = store.stateFlow

    override fun onClickChangeFavourite() {
        store.accept(DetailsStore.Intent.ClickFavouriteStatus)
    }

    override fun onClickBack() {
        store.accept(DetailsStore.Intent.ClickBack)
    }
}