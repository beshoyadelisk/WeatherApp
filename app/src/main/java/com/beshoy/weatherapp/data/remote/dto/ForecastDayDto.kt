package com.beshoy.weatherapp.data.remote.dto


import com.beshoy.weatherapp.domain.model.ForecastDay
import com.beshoy.weatherapp.domain.model.Units
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ForecastDayDto(
    @SerialName("astro")
    val astroDto: AstroDto,
    @SerialName("date")
    val date: String,
    @SerialName("date_epoch")
    val dateEpoch: Int,
    @SerialName("day")
    val dayDto: DayDto,
    @SerialName("hour")
    val hourDto: List<HourDto>
)

fun ForecastDayDto.asExternalModel(units: Units) = ForecastDay(
    astro = astroDto.asExternalModel(),
    date = date,
    day = dayDto.asExternalModel(units),
    weatherHours = hourDto.map { it.asExternalModel(units) },
    dateEpoch = dateEpoch
)