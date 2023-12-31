package com.beshoy.weatherapp.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.beshoy.weatherapp.domain.model.DefaultLocation
import com.beshoy.weatherapp.domain.model.Units
import com.beshoy.weatherapp.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : SettingsRepository {

    private val prefLatLong by lazy { stringPreferencesKey("lat_long") }
    private val prefUnit by lazy { stringPreferencesKey("units") }

    private val defLong = 30.044612
    private val defLat = 31.236673

    override suspend fun setUnits(units: Units) {
        dataStore.edit { settings ->
            settings[prefUnit] = units.name
        }
    }

    override suspend fun getUnits(): Flow<Units> =
        dataStore.data.map { settings ->
            settings[prefUnit] ?: Units.METRIC.name
        }.map { unitName ->
            Units.valueOf(unitName)
        }

    override suspend fun setDefaultLocation(defaultLocation: DefaultLocation) {
        dataStore.edit { settings ->
            settings[prefLatLong] = "${defaultLocation.latitude},${defaultLocation.longitude}"
        }
    }

    override suspend fun getDefaultLocation(): Flow<DefaultLocation> {
        return dataStore.data.map { settings ->
            settings[prefLatLong] ?: "$defLat,$defLong"
        }.map { latLong ->
            val latLngList = latLong.split(",").map { it.toDouble() }
            DefaultLocation(latitude = latLngList[0], longitude = latLngList[1])
        }
    }

}