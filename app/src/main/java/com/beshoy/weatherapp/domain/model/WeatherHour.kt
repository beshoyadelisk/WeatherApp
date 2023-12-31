package com.beshoy.weatherapp.domain.model

data class WeatherHour(
    val condition: Condition,
    val temp: Double,
    val time: String,
    val timeEpoch: Int,
)
