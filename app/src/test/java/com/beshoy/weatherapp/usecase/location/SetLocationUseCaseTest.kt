package com.beshoy.weatherapp.usecase.location

import com.beshoy.weatherapp.domain.model.DefaultLocation
import com.beshoy.weatherapp.domain.repository.SettingsRepository
import com.beshoy.weatherapp.domain.usecase.settings.location.GetLocationUseCase
import com.beshoy.weatherapp.domain.usecase.settings.location.SetLocationUseCase
import com.beshoy.weatherapp.repository.settings.FakeSettingsRepository
import com.google.common.truth.Truth
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class SetLocationUseCaseTest {
    private lateinit var setLocationUseCase: SetLocationUseCase
    private lateinit var getLocationUseCase: GetLocationUseCase
    private lateinit var fakeSettingsRepository: SettingsRepository

    @Before
    fun setup() {
        fakeSettingsRepository = FakeSettingsRepository()
        setLocationUseCase = SetLocationUseCase(fakeSettingsRepository)
        getLocationUseCase = GetLocationUseCase(fakeSettingsRepository)
    }

    @Test
    fun `When set location correct location return`() = runBlocking {
        val newLocation = DefaultLocation(1.1, 2.2)
        setLocationUseCase(newLocation)
        val savedLocation = getLocationUseCase().first()
        Truth.assertThat(savedLocation.latitude).isEqualTo(newLocation.latitude)
        Truth.assertThat(savedLocation.longitude).isEqualTo(newLocation.longitude)
    }

}