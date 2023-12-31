package com.beshoy.weatherapp.domain.model

data class DefaultLocation(val longitude: Double, val latitude: Double) {
    override fun toString(): String {
        return "$latitude,$longitude"
    }
}
