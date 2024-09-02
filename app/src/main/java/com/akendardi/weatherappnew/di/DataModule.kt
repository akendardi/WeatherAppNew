package com.akendardi.weatherappnew.di

import android.content.ContentProvider
import android.content.Context
import com.akendardi.weatherappnew.data.local.db.FavouriteCitiesDao
import com.akendardi.weatherappnew.data.local.db.FavouriteDatabase
import com.akendardi.weatherappnew.data.network.api.ApiFactory
import com.akendardi.weatherappnew.data.network.api.ApiService
import com.akendardi.weatherappnew.data.repository.FavouriteRepositoryImpl
import com.akendardi.weatherappnew.data.repository.SearchRepositoryImpl
import com.akendardi.weatherappnew.data.repository.WeatherRepositoryImpl
import com.akendardi.weatherappnew.domain.repository.FavouriteRepository
import com.akendardi.weatherappnew.domain.repository.SearchRepository
import com.akendardi.weatherappnew.domain.repository.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @Binds
    @ApplicationScope
    fun bindFavouriteRepository(impl: FavouriteRepositoryImpl): FavouriteRepository

    @Binds
    @ApplicationScope
    fun bindSearchRepository(impl: SearchRepositoryImpl): SearchRepository

    @Binds
    @ApplicationScope
    fun bindsWeatherRepository(impl: WeatherRepositoryImpl): WeatherRepository

    companion object{

        @Provides
        @ApplicationScope
        fun provideApiService(): ApiService{
            return ApiFactory.apiService
        }

        @Provides
        @ApplicationScope
        fun provideFavouriteDatabase(context: Context): FavouriteDatabase {
            return FavouriteDatabase.getInstance(context)
        }

        @Provides
        @ApplicationScope
        fun provideFavouriteCitiesDao(database: FavouriteDatabase): FavouriteCitiesDao{
            return database.favouriteCitiesDao()
        }
    }


}