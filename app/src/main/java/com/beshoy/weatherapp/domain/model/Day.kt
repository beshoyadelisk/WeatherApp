package com.beshoy.weatherapp.domain.model

data class Day(
    val maxTemp: Double,
    val minTemp: Double,
    val avgTemp:Double,
    val condition: Condition
)
