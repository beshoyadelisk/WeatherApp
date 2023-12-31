package com.beshoy.weatherapp.data.remote.dto


import com.beshoy.weatherapp.domain.model.Units
import com.beshoy.weatherapp.domain.model.WeatherHour
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HourDto(
    @SerialName("chance_of_rain")
    val chanceOfRain: Int,
    @SerialName("chance_of_snow")
    val chanceOfSnow: Int,
    @SerialName("cloud")
    val cloud: Int,
    @SerialName("condition")
    val conditionDto: ConditionDto,
    @SerialName("dewpoint_c")
    val dewpointC: Double,
    @SerialName("dewpoint_f")
    val dewpointF: Double,
    @SerialName("feelslike_c")
    val feelslikeC: Double,
    @SerialName("feelslike_f")
    val feelslikeF: Double,
    @SerialName("gust_kph")
    val gustKph: Double,
    @SerialName("gust_mph")
    val gustMph: Double,
    @SerialName("heatindex_c")
    val heatindexC: Double,
    @SerialName("heatindex_f")
    val heatindexF: Double,
    @SerialName("humidity")
    val humidity: Int,
    @SerialName("is_day")
    val isDay: Int,
    @SerialName("precip_in")
    val precipIn: Double,
    @SerialName("precip_mm")
    val precipMm: Double,
    @SerialName("pressure_in")
    val pressureIn: Double,
    @SerialName("pressure_mb")
    val pressureMb: Double,
    @SerialName("snow_cm")
    val snowCm: Double,
    @SerialName("temp_c")
    val tempC: Double,
    @SerialName("temp_f")
    val tempF: Double,
    @SerialName("time")
    val time: String,
    @SerialName("time_epoch")
    val timeEpoch: Int,
    @SerialName("uv")
    val uv: Double,
    @SerialName("vis_km")
    val visKm: Double,
    @SerialName("vis_miles")
    val visMiles: Double,
    @SerialName("will_it_rain")
    val willItRain: Int,
    @SerialName("will_it_snow")
    val willItSnow: Int,
    @SerialName("wind_degree")
    val windDegree: Int,
    @SerialName("wind_dir")
    val windDir: String,
    @SerialName("wind_kph")
    val windKph: Double,
    @SerialName("wind_mph")
    val windMph: Double,
    @SerialName("windchill_c")
    val windchillC: Double,
    @SerialName("windchill_f")
    val windchillF: Double
)

fun HourDto.asExternalModel(units: Units) = WeatherHour(
    condition = conditionDto.asExternalModel(),
    temp = when (units) {
        Units.METRIC -> tempC
        Units.IMPERIAL -> tempF
    },
    time = time,
    timeEpoch = timeEpoch
)