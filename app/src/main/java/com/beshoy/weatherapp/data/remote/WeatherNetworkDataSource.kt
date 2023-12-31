package com.beshoy.weatherapp.data.remote

import com.beshoy.weatherapp.data.Result
import com.beshoy.weatherapp.domain.model.DefaultLocation
import com.beshoy.weatherapp.domain.model.Units
import com.beshoy.weatherapp.domain.model.Weather

interface WeatherNetworkDataSource {
    suspend fun getForecast(
        location: DefaultLocation,
        days: Int,
        units: Units
    ): Result<Weather>
}