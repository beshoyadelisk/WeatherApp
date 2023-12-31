package com.beshoy.weatherapp.data.remote.dto


import com.beshoy.weatherapp.domain.model.Units
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ForecastDto(
    @SerialName("forecastday")
    val forecastDayDto: List<ForecastDayDto>
)

fun ForecastDto.asExternalModel(units: Units) = forecastDayDto.map { it.asExternalModel(units) }