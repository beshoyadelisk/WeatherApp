package com.beshoy.weatherapp.data.repository

import com.beshoy.weatherapp.data.Result
import com.beshoy.weatherapp.data.remote.WeatherNetworkDataSource
import com.beshoy.weatherapp.data.remote.mapThrowableToErrorType
import com.beshoy.weatherapp.domain.model.DefaultLocation
import com.beshoy.weatherapp.domain.model.Units
import com.beshoy.weatherapp.domain.model.Weather
import com.beshoy.weatherapp.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherNetworkDataSource: WeatherNetworkDataSource
) : WeatherRepository {
    override suspend fun getForecast(
        location: DefaultLocation,
        days: Int,
        units: Units
    ): Result<Weather> = try {
        weatherNetworkDataSource.getForecast(location, days, units)
    } catch (throwable: Throwable) {
        val errorType = mapThrowableToErrorType(throwable)
        Result.Error(errorType)
    }
}