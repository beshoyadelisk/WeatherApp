package com.beshoy.weatherapp.usecase.units

import com.beshoy.weatherapp.domain.model.Units
import com.beshoy.weatherapp.domain.repository.SettingsRepository
import com.beshoy.weatherapp.domain.usecase.settings.unit.GetUnitUseCase
import com.beshoy.weatherapp.repository.settings.FakeSettingsRepository
import com.google.common.truth.Truth
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetUnitUseCaseTest  {

    private lateinit var getUnitUseCase: GetUnitUseCase
    private lateinit var fakeSettingsRepository: SettingsRepository

    @Before
    fun setup() {
        fakeSettingsRepository = FakeSettingsRepository()
        getUnitUseCase = GetUnitUseCase(fakeSettingsRepository)
    }

    @Test
    fun `Get Default Unit, correct unit return`() = runBlocking {
        val unit = getUnitUseCase().first()
        Truth.assertThat(unit == Units.METRIC).isTrue()
    }
    @Test
    fun `Get Default Unit, incorrect unit return`() = runBlocking {
        val unit = getUnitUseCase().first()
        Truth.assertThat(unit == Units.IMPERIAL).isFalse()
    }

}