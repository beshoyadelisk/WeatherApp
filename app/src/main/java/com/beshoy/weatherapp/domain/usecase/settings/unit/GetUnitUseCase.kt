package com.beshoy.weatherapp.domain.usecase.settings.unit

import com.beshoy.weatherapp.domain.model.Units
import com.beshoy.weatherapp.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUnitUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke(): Flow<Units> = settingsRepository.getUnits()
}
