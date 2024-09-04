package com.akendardi.weatherappnew.presentation.favourite

import com.akendardi.weatherappnew.domain.entity.City
import com.akendardi.weatherappnew.domain.usecase.GetFavouriteCitiesUseCase
import com.akendardi.weatherappnew.domain.usecase.GetWeatherUseCase
import com.akendardi.weatherappnew.presentation.favourite.FavouriteStore.Intent
import com.akendardi.weatherappnew.presentation.favourite.FavouriteStore.Label
import com.akendardi.weatherappnew.presentation.favourite.FavouriteStore.State
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import kotlinx.coroutines.launch
import javax.inject.Inject

interface FavouriteStore : Store<Intent, State, Label> {

    sealed interface Intent {

        data object ClickSearch : Intent

        data object ClickToAddFavourite : Intent

        data class CityItemClick(val city: City) : Intent
    }

    data class State(
        val citiesItem: List<CityItem>
    ) {

        data class CityItem(
            val city: City,
            val weatherState: WeatherState
        )

        sealed interface WeatherState {
            data object Initial : WeatherState

            data object Loading : WeatherState

            data object Error : WeatherState

            data class Loaded(
                val tempC: Float,
                val iconUrl: String
            ) : WeatherState
        }

    }

    sealed interface Label {

        data object ClickSearch : Label

        data object ClickToFavourite : Label

        data class CityItemClick(val city: City) : Label
    }
}

class FavouriteStoreFactory @Inject constructor(
    private val storeFactory: StoreFactory,
    private val getFavouriteCitiesUsecase: GetFavouriteCitiesUseCase,
    private val getCurrentWeatherUsecase: GetWeatherUseCase
) {

    fun create(): FavouriteStore =
        object : FavouriteStore, Store<Intent, State, Label> by storeFactory.create(
            name = "FavouriteStore",
            initialState = State(listOf()),
            bootstrapper = BootstrapperImpl(),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed interface Action {

        data class FavouriteCitiesLoaded(val cities: List<City>) : Action
    }

    private sealed interface Msg {

        data class FavouriteCitiesLoaded(val cities: List<City>) : Msg

        data class WeatherLoaded(
            val cityId: Int,
            val tempC: Float,
            val iconUrl: String
        ) : Msg

        data class WeatherLoadingError(
            val cityId: Int
        ) : Msg

        data class WeatherIsLoading(
            val cityId: Int
        ) : Msg
    }

    private inner class BootstrapperImpl : CoroutineBootstrapper<Action>() {
        override fun invoke() {
            scope.launch {
                getFavouriteCitiesUsecase().collect {
                    dispatch(Action.FavouriteCitiesLoaded(it))
                }
            }
        }
    }

    private inner class ExecutorImpl : CoroutineExecutor<Intent, Action, State, Msg, Label>() {
        override fun executeIntent(intent: Intent, getState: () -> State) {
            when(intent){
                is Intent.CityItemClick -> {
                    publish(Label.CityItemClick(intent.city))
                }
                Intent.ClickSearch -> {
                    publish(Label.ClickSearch)
                }
                Intent.ClickToAddFavourite -> {
                    publish(Label.ClickToFavourite)
                }
            }
        }

        override fun executeAction(action: Action, getState: () -> State) {
            when (action) {
                is Action.FavouriteCitiesLoaded -> {
                    dispatch(Msg.FavouriteCitiesLoaded(action.cities))
                    action.cities.forEach {
                        scope.launch {
                            loadWeatherForCity(city = it)
                        }
                    }
                }
            }
        }

        private suspend fun loadWeatherForCity(city: City) {
            dispatch(Msg.WeatherIsLoading(city.id))
            try {
                val weather = getCurrentWeatherUsecase(city.id)
                dispatch(
                    Msg.WeatherLoaded(
                        cityId = city.id,
                        tempC = weather.tempC,
                        iconUrl = weather.conditionUrl
                    )
                )
            } catch (e: Exception) {
                dispatch(Msg.WeatherLoadingError(city.id))
            }

        }
    }

    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(msg: Msg): State {
            when (msg) {
                is Msg.FavouriteCitiesLoaded -> {
                    return copy(
                        citiesItem = msg.cities.map {
                            State.CityItem(
                                city = it,
                                weatherState = State.WeatherState.Initial
                            )
                        }
                    )
                }

                is Msg.WeatherIsLoading -> {
                    return copy(
                        citiesItem = citiesItem.map {
                            if (it.city.id == msg.cityId) {
                                it.copy(
                                    weatherState = State.WeatherState.Loading
                                )
                            } else {
                                it
                            }
                        }
                    )
                }

                is Msg.WeatherLoaded -> {
                    return copy(
                        citiesItem = citiesItem.map {
                            if (it.city.id == msg.cityId) {
                                it.copy(
                                    weatherState = State.WeatherState.Loaded(
                                        msg.tempC,
                                        msg.iconUrl
                                    )
                                )
                            } else {
                                it
                            }
                        }
                    )

                }

                is Msg.WeatherLoadingError -> {
                    return copy(
                        citiesItem = citiesItem.map {
                            if (it.city.id == msg.cityId) {
                                it.copy(
                                    weatherState = State.WeatherState.Error
                                )
                            } else {
                                it
                            }
                        }
                    )
                }
            }
        }
    }
}
