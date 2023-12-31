package com.beshoy.weatherapp.domain.model

data class ForecastDay(
    val astro: Astro,
    val date: String,
    val dateEpoch: Int,
    val day: Day,
    val weatherHours: List<WeatherHour>
)
