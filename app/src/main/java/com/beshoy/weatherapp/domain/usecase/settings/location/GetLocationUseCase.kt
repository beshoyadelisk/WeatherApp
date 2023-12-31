package com.beshoy.weatherapp.domain.usecase.settings.location

import com.beshoy.weatherapp.domain.model.DefaultLocation
import com.beshoy.weatherapp.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLocationUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke(): Flow<DefaultLocation> = settingsRepository.getDefaultLocation()
}
