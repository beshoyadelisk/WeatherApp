package com.beshoy.weatherapp.data.remote.dto


import com.beshoy.weatherapp.domain.model.Condition
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ConditionDto(
    @SerialName("code")
    val code: Int,
    @SerialName("icon")
    val icon: String,
    @SerialName("text")
    val text: String
)

fun ConditionDto.asExternalModel() = Condition(
    conditionText = text, icon = "https:/$icon"
)