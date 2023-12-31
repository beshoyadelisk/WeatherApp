package com.beshoy.weatherapp.domain.usecase

import com.beshoy.weatherapp.data.Result
import com.beshoy.weatherapp.domain.model.DefaultLocation
import com.beshoy.weatherapp.domain.model.Units
import com.beshoy.weatherapp.domain.model.Weather
import com.beshoy.weatherapp.domain.repository.WeatherRepository
import javax.inject.Inject

class GetWeatherUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {
    suspend operator fun invoke(
        location: DefaultLocation,
        days: Int,
        units: Units
    ): Result<Weather> =
        weatherRepository.getForecast(location, days, units)
}
