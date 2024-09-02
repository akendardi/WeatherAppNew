package com.akendardi.weatherappnew

import android.app.Application
import com.akendardi.weatherappnew.di.AppComponent
import com.akendardi.weatherappnew.di.DaggerAppComponent

class WeatherApp : Application() {

    lateinit var applicationComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        applicationComponent = DaggerAppComponent.factory().create(this)
    }
}