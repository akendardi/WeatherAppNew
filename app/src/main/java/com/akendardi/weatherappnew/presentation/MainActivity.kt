package com.akendardi.weatherappnew.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.akendardi.weatherappnew.WeatherApp
import com.akendardi.weatherappnew.data.network.api.ApiFactory
import com.akendardi.weatherappnew.domain.usecase.ChangeFavouriteStateUseCase
import com.akendardi.weatherappnew.domain.usecase.SearchCityUseCase
import com.akendardi.weatherappnew.presentation.root.RootComponentImpl
import com.akendardi.weatherappnew.presentation.root.RootContent
import com.akendardi.weatherappnew.presentation.theme.WeatherAppNewTheme
import com.arkivanov.decompose.defaultComponentContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var rootComponentFactory: RootComponentImpl.Factory


    override fun onCreate(savedInstanceState: Bundle?) {
        (applicationContext as WeatherApp).applicationComponent.inject(this)
        super.onCreate(savedInstanceState)


        setContent {
            WeatherAppNewTheme {
                RootContent(rootComponentFactory.create(defaultComponentContext()))
            }
        }
    }
}
