package com.beshoy.weatherapp.di

import com.beshoy.weatherapp.data.remote.WeatherNetworkDataSource
import com.beshoy.weatherapp.data.remote.WeatherNetworkDataSourceImpl
import com.beshoy.weatherapp.data.repository.SettingsRepositoryImpl
import com.beshoy.weatherapp.data.repository.WeatherRepositoryImpl
import com.beshoy.weatherapp.domain.repository.SettingsRepository
import com.beshoy.weatherapp.domain.repository.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface RepositoryModule {
    @Binds
    fun bindWeatherRepository(weatherRepository: WeatherRepositoryImpl): WeatherRepository

    @Binds
    fun bindSettingsRepository(settingsRepository: SettingsRepositoryImpl): SettingsRepository

    @Binds
    fun bindRemoteWeatherDataSource(remoteWeatherDataSource: WeatherNetworkDataSourceImpl): WeatherNetworkDataSource

}