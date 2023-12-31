package com.beshoy.weatherapp.domain.model

enum class Units(val value: String, val tempLabel: String) {
    METRIC("metric","°C"),
    IMPERIAL("imperial","°F"),
}
