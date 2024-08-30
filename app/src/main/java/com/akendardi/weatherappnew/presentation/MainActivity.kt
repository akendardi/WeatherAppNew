package com.akendardi.weatherappnew.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.akendardi.weatherappnew.data.network.api.ApiFactory
import com.akendardi.weatherappnew.presentation.theme.WeatherAppNewTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val apiService = ApiFactory.apiService
        CoroutineScope(Dispatchers.Main).launch {
            val current = apiService.loadCurrentWeather("London")
            val forecast = apiService.loadForecast("London")
            val cities = apiService.searchCity("London")
            Log.d("TEST_TEST", current.toString())
            Log.d("TEST_TEST", forecast.toString())
            Log.d("TEST_TEST", cities.toString())
        }
        setContent {
            WeatherAppNewTheme {

            }
        }
    }
}
