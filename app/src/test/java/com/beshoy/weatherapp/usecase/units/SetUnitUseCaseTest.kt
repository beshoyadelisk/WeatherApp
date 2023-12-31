package com.beshoy.weatherapp.usecase.units

import com.beshoy.weatherapp.domain.model.Units
import com.beshoy.weatherapp.domain.repository.SettingsRepository
import com.beshoy.weatherapp.domain.usecase.settings.unit.GetUnitUseCase
import com.beshoy.weatherapp.domain.usecase.settings.unit.SetUnitUseCase
import com.beshoy.weatherapp.repository.settings.FakeSettingsRepository
import com.google.common.truth.Truth
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class SetUnitUseCaseTest {
    private lateinit var setUnitUseCase: SetUnitUseCase
    private lateinit var getUnitUseCase: GetUnitUseCase
    private lateinit var fakeSettingsRepository: SettingsRepository

    @Before
    fun setup() {
        fakeSettingsRepository = FakeSettingsRepository()
        setUnitUseCase = SetUnitUseCase(fakeSettingsRepository)
        getUnitUseCase = GetUnitUseCase(fakeSettingsRepository)
    }

    @Test
    fun `When set unit correct unit return`() = runBlocking {
        setUnitUseCase(Units.IMPERIAL)
        val savedUnit = getUnitUseCase().first()
        Truth.assertThat(savedUnit==Units.IMPERIAL).isTrue()
    }

}