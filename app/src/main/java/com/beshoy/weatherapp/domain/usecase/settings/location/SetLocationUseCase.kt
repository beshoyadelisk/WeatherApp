package com.beshoy.weatherapp.domain.usecase.settings.location

import com.beshoy.weatherapp.domain.model.DefaultLocation
import com.beshoy.weatherapp.domain.repository.SettingsRepository
import javax.inject.Inject

class SetLocationUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke(location: DefaultLocation) = settingsRepository.setDefaultLocation(location)
}
