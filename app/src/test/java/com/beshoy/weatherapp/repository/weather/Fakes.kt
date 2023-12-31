package com.beshoy.weatherapp.repository.weather

import com.beshoy.weatherapp.data.remote.dto.ConditionDto
import com.beshoy.weatherapp.data.remote.dto.CurrentDto
import com.beshoy.weatherapp.data.remote.dto.ForecastDto
import com.beshoy.weatherapp.data.remote.dto.LocationDto
import com.beshoy.weatherapp.data.remote.dto.WeatherDto
import com.beshoy.weatherapp.domain.model.Condition
import com.beshoy.weatherapp.domain.model.CurrentWeather
import com.beshoy.weatherapp.domain.model.Weather
import com.beshoy.weatherapp.domain.model.WeatherLocation

val fakeSuccessWeather = WeatherDto(
    currentDto = CurrentDto(
        cloud = 0,
        conditionDto = ConditionDto(code = 0, icon = "", text = ""),
        feelslikeC = 10.0,
        feelslikeF = 0.0,
        gustKph = 0.0,
        gustMph = 0.0,
        isDay = 1,
        humidity = 0,
        lastUpdated = "",
        lastUpdatedEpoch = 1,
        precipIn = 0.0,
        precipMm = 0.0,
        pressureIn = 0.0,
        pressureMb = 0.0,
        tempC = 13.0,
        tempF = 0.0,
        uv = 0.0,
        visKm = 0.0,
        visMiles = 0.0,
        windDegree = 0,
        windDir = "",
        windKph = 0.0,
        windMph = 0.0

    ),
    forecastDto = ForecastDto(listOf()),
    locationDto = LocationDto(
        country = "",
        lat = 0.0,
        localtime = "",
        localtimeEpoch = 1,
        lon = 0.0,
        name = "",
        region = "",
        tzId = ""
    )
)


val fakeSuccessMappedWeather = Weather(
    currentWeather = CurrentWeather(
        condition = Condition("", "https:/"),
        isDay = 1,
        lastUpdated = "",
        temp = 13.0,
        feelLike = 10.0,
        windKph = 0.0,
        windMph = 0.0
    ),
    forecast = listOf(),
    weatherLocation = WeatherLocation(
        country = "",
        lat = 0.0,
        localtime = "",
        localtimeEpoch = 1,
        lon = 0.0,
        city = "",
        region = ""
    )
)
