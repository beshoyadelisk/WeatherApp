package com.beshoy.weatherapp.domain.usecase.settings.unit

import com.beshoy.weatherapp.domain.model.Units
import com.beshoy.weatherapp.domain.repository.SettingsRepository
import javax.inject.Inject

class SetUnitUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke(units: Units) = settingsRepository.setUnits(units)
}
