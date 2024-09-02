package com.akendardi.weatherappnew.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component

@Component(
    modules = [
        DataModule::class,
        PresentationModule::class
    ]
)
@ApplicationScope
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }
}