package com.beshoy.weatherapp.repository.settings

import com.beshoy.weatherapp.domain.model.DefaultLocation
import com.beshoy.weatherapp.domain.model.Units
import com.beshoy.weatherapp.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeSettingsRepository : SettingsRepository {

    private val settingsMap = mutableMapOf<String, String>()


    override suspend fun setUnits(units: Units) {
        settingsMap["units"] = units.name
    }

    override suspend fun getUnits(): Flow<Units> = flow {
        val value = settingsMap["units"] ?: Units.METRIC.name
        emit(Units.valueOf(value))
    }

    override suspend fun setDefaultLocation(defaultLocation: DefaultLocation) {
        settingsMap["latlng"] = "${defaultLocation.latitude}/${defaultLocation.longitude}"
    }

    override suspend fun getDefaultLocation(): Flow<DefaultLocation> = flow {
        val latLng = settingsMap["latlng"] ?: "0.0/0.0"
        val latLngList = latLng.split("/")
        emit(DefaultLocation(latitude = latLngList[0].toDouble(), longitude = latLngList[1].toDouble()))
    }

}
