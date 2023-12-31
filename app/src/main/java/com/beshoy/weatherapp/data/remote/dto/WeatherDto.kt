package com.beshoy.weatherapp.data.remote.dto


import com.beshoy.weatherapp.domain.model.Units
import com.beshoy.weatherapp.domain.model.Weather
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherDto(
    @SerialName("current")
    val currentDto: CurrentDto,
    @SerialName("forecast")
    val forecastDto: ForecastDto,
    @SerialName("location")
    val locationDto: LocationDto
)

fun WeatherDto.asExternalModel(units: Units) = Weather(
    currentWeather = currentDto.asExternalModel(units),
    forecast = forecastDto.forecastDayDto.map{it.asExternalModel(units)},
    weatherLocation = locationDto.asExternalModel()
)
