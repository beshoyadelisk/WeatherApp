package com.beshoy.weatherapp.data.remote.dto


import com.beshoy.weatherapp.domain.model.CurrentWeather
import com.beshoy.weatherapp.domain.model.Units
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CurrentDto(
    @SerialName("cloud")
    val cloud: Int,
    @SerialName("condition")
    val conditionDto: ConditionDto,
    @SerialName("feelslike_c")
    val feelslikeC: Double,
    @SerialName("feelslike_f")
    val feelslikeF: Double,
    @SerialName("gust_kph")
    val gustKph: Double,
    @SerialName("gust_mph")
    val gustMph: Double,
    @SerialName("humidity")
    val humidity: Int,
    @SerialName("is_day")
    val isDay: Int,
    @SerialName("last_updated")
    val lastUpdated: String,
    @SerialName("last_updated_epoch")
    val lastUpdatedEpoch: Int,
    @SerialName("precip_in")
    val precipIn: Double,
    @SerialName("precip_mm")
    val precipMm: Double,
    @SerialName("pressure_in")
    val pressureIn: Double,
    @SerialName("pressure_mb")
    val pressureMb: Double,
    @SerialName("temp_c")
    val tempC: Double,
    @SerialName("temp_f")
    val tempF: Double,
    @SerialName("uv")
    val uv: Double,
    @SerialName("vis_km")
    val visKm: Double,
    @SerialName("vis_miles")
    val visMiles: Double,
    @SerialName("wind_degree")
    val windDegree: Int,
    @SerialName("wind_dir")
    val windDir: String,
    @SerialName("wind_kph")
    val windKph: Double,
    @SerialName("wind_mph")
    val windMph: Double
)

fun CurrentDto.asExternalModel(units: Units) = CurrentWeather(
    condition = conditionDto.asExternalModel(),
    isDay = isDay,
    lastUpdated = lastUpdated,
    windKph = windKph,
    windMph = windMph,
    temp = when (units) {
        Units.METRIC -> tempC
        Units.IMPERIAL -> tempF
    },
    feelLike = when (units) {
        Units.METRIC -> feelslikeC
        Units.IMPERIAL -> feelslikeF
    }
)