package com.akendardi.weatherappnew.presentation.search

import com.akendardi.weatherappnew.domain.entity.City
import com.akendardi.weatherappnew.domain.usecase.ChangeFavouriteStateUseCase
import com.akendardi.weatherappnew.domain.usecase.SearchCityUseCase
import com.akendardi.weatherappnew.presentation.search.SearchStore.Intent
import com.akendardi.weatherappnew.presentation.search.SearchStore.Label
import com.akendardi.weatherappnew.presentation.search.SearchStore.State
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

interface SearchStore : Store<Intent, State, Label> {

    sealed interface Intent {

        data object ClickBack : Intent

        data object ClickSearch : Intent

        data class ClickCity(val city: City) : Intent

        data class ChangeSearchQuery(val query: String) : Intent


    }

    data class State(
        val searchQuery: String,
        val searchState: SearchState
    ) {

        sealed interface SearchState {

            data object Initial : SearchState

            data object Loading : SearchState

            data object Error : SearchState

            data object EmptyResult : SearchState

            data class SuccessLoaded(val cities: List<City>) : SearchState
        }
    }

    sealed interface Label {

        data object ClickBack : Label

        data object SavedToFavourite : Label

        data class OpenForecast(val city: City) : Label
    }
}

class SearchStoreFactory @Inject constructor(
    private val storeFactory: StoreFactory,
    private val searchCityUseCase: SearchCityUseCase,
    private val changeFavouriteStateUseCase: ChangeFavouriteStateUseCase
) {

    fun create(openReason: OpenReason): SearchStore =
        object : SearchStore, Store<Intent, State, Label> by storeFactory.create(
            name = "SearchStore",
            initialState = State(
                searchQuery = "",
                searchState = State.SearchState.Initial
            ),
            bootstrapper = BootstrapperImpl(),
            executorFactory = { ExecutorImpl(openReason) },
            reducer = ReducerImpl
        ) {}

    private sealed interface Action

    private sealed interface Msg {
        data class ChangeSearchQuery(val query: String) : Msg

        data object LoadingSearhResult : Msg

        data object SearchResultError : Msg

        data class SearchResultLoaded(val cities: List<City>) : Msg


    }

    private class BootstrapperImpl : CoroutineBootstrapper<Action>() {
        override fun invoke() {
        }
    }

    private inner class ExecutorImpl(private val openReason: OpenReason) :
        CoroutineExecutor<Intent, Action, State, Msg, Label>() {
        private var searchJob: Job? = null
        override fun executeIntent(intent: Intent, getState: () -> State) {

            when (intent) {
                Intent.ClickBack -> {
                    publish(Label.ClickBack)
                }

                is Intent.ClickCity -> {
                    when (openReason) {
                        OpenReason.Add -> {
                            scope.launch {
                                changeFavouriteStateUseCase.addToFavourite(intent.city)
                                publish(Label.SavedToFavourite)
                            }
                        }

                        OpenReason.RegularOpen -> {
                            publish(Label.OpenForecast(intent.city))
                        }
                    }
                }

                Intent.ClickSearch -> {
                    searchJob?.cancel()
                    searchJob = scope.launch {
                        dispatch(Msg.LoadingSearhResult)
                        try {
                            val cities = searchCityUseCase(getState().searchQuery)
                            dispatch(Msg.SearchResultLoaded(cities))
                        } catch (e: Exception) {
                            dispatch(Msg.SearchResultError)
                        }
                    }
                }

                is Intent.ChangeSearchQuery -> {
                    dispatch(Msg.ChangeSearchQuery(intent.query))
                }
            }
        }

    }

    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(message: Msg): State = when (message) {
            is Msg.ChangeSearchQuery -> {
                copy(searchQuery = message.query)
            }

            Msg.LoadingSearhResult -> {
                copy(searchState = State.SearchState.Loading)
            }

            Msg.SearchResultError -> {
                copy(searchState = State.SearchState.Error)
            }

            is Msg.SearchResultLoaded -> {
                if (message.cities.isEmpty()) {
                    copy(searchState = State.SearchState.EmptyResult)
                } else {
                    copy(searchState = State.SearchState.SuccessLoaded(message.cities))
                }
            }
        }
    }
}
