package com.beshoy.weatherapp.usecase.location

import com.beshoy.weatherapp.domain.repository.SettingsRepository
import com.beshoy.weatherapp.domain.usecase.settings.location.GetLocationUseCase
import com.beshoy.weatherapp.repository.settings.FakeSettingsRepository
import com.google.common.truth.Truth
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetLocationUseCaseTest {

    private lateinit var getLocationUseCase: GetLocationUseCase
    private lateinit var fakeSettingsRepository: SettingsRepository

    @Before
    fun setup() {
        fakeSettingsRepository = FakeSettingsRepository()
        getLocationUseCase = GetLocationUseCase(fakeSettingsRepository)
    }

    @Test
    fun `Get Default Location, correct location return`() = runBlocking {
        val location = getLocationUseCase().first()
        Truth.assertThat(location.latitude).isEqualTo(0.0)
        Truth.assertThat(location.longitude).isEqualTo(0.0)
    }
    @Test
    fun `Get Default Location, incorrect location return`() = runBlocking {
        val location = getLocationUseCase().first()
        Truth.assertThat(location.latitude== 1.1).isFalse()
        Truth.assertThat(location.longitude==1.1).isFalse()
    }

}