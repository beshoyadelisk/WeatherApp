package com.beshoy.weatherapp.domain.model

data class WeatherLocation(
    val country: String,
    val lat: Double,
    val lon: Double,
    val localtime: String,
    val localtimeEpoch: Int,
    val city: String,
    val region: String,
)
