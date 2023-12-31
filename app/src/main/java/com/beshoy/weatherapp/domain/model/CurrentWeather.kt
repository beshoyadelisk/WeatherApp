package com.beshoy.weatherapp.domain.model

data class CurrentWeather(
    val condition: Condition,
    val isDay: Int,
    val lastUpdated: String,
    val temp: Double,
    val feelLike: Double,
    val windKph: Double,
    val windMph: Double
)
