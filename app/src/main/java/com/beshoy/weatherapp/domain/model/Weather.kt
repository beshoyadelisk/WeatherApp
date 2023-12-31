package com.beshoy.weatherapp.domain.model

data class Weather(
    val currentWeather: CurrentWeather,
    val forecast: List<ForecastDay>,
    val weatherLocation: WeatherLocation
)

 val fakeSuccessMappedWeatherResponse =
    Weather(
        currentWeather = CurrentWeather(
            condition = Condition("Rain", ""),
            isDay = 1,
            lastUpdated = "",
            windMph = 0.0,
            windKph = 0.0,
            temp = 30.0,
            feelLike = 10.0
        ),
        forecast = listOf(
            ForecastDay(
                astro = Astro("", ""),
                date = "2023-12-30",
                dateEpoch = 1703894400,
                day = Day(
                    maxTemp = 15.0,
                    minTemp = 10.0,
                    avgTemp = 12.0,
                    condition = Condition(
                        "Patchy rain possible",
                        icon = "//cdn.weatherapi.com/weather/64x64/day/176.png"
                    )
                ),
                weatherHours = listOf()
            )
        ),
        weatherLocation = WeatherLocation(
            country = "Egypt",
            lat = 0.0,
            lon = 0.0,
            localtime = "",
            localtimeEpoch = 1,
            city = "Cairo",
            region = "Cairo, Egypt"
        )
    )
