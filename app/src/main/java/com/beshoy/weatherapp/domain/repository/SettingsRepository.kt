package com.beshoy.weatherapp.domain.repository

import com.beshoy.weatherapp.domain.model.DefaultLocation
import com.beshoy.weatherapp.domain.model.Units
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    suspend fun setUnits(units: Units)

    suspend fun getUnits(): Flow<Units>

    suspend fun setDefaultLocation(defaultLocation: DefaultLocation)

    suspend fun getDefaultLocation(): Flow<DefaultLocation>
}
