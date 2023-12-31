package com.beshoy.weatherapp.usecase

import com.beshoy.weatherapp.data.Result
import com.beshoy.weatherapp.data.remote.WeatherAPI
import com.beshoy.weatherapp.data.remote.WeatherNetworkDataSource
import com.beshoy.weatherapp.data.remote.WeatherNetworkDataSourceImpl
import com.beshoy.weatherapp.data.repository.WeatherRepositoryImpl
import com.beshoy.weatherapp.domain.model.DefaultLocation
import com.beshoy.weatherapp.domain.model.Units
import com.beshoy.weatherapp.domain.repository.WeatherRepository
import com.beshoy.weatherapp.domain.usecase.GetWeatherUseCase
import com.beshoy.weatherapp.repository.weather.fakeSuccessMappedWeather
import com.beshoy.weatherapp.repository.weather.fakeSuccessWeather
import com.google.common.truth.Truth
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class GetWeatherUseCaseTest {
    @MockK
    val mockWeatherService = mockk<WeatherAPI>(relaxed = true)

    private lateinit var getWeatherUseCase: GetWeatherUseCase

    private val defaultLocation = DefaultLocation(0.0, 0.0)


    private fun createWeatherRepository(
        weatherNetworkDataSource: WeatherNetworkDataSource =
            WeatherNetworkDataSourceImpl(weatherAPI = mockWeatherService)
    ): WeatherRepository = WeatherRepositoryImpl(
        weatherNetworkDataSource = weatherNetworkDataSource
    )

    @Before
    fun setup() {
        getWeatherUseCase = GetWeatherUseCase(createWeatherRepository())
    }

    @Test
    fun `Get Default Weather, correct weather return`() = runBlocking {
        coEvery {
            mockWeatherService.getForecast(any(), any(), any())
        } returns Response.success(
            fakeSuccessWeather
        )
        val expectedResult = fakeSuccessMappedWeather
        val actualResults = getWeatherUseCase(defaultLocation, 7, Units.METRIC)

        Truth.assertThat(actualResults).isInstanceOf(Result.Success::class.java)
        Truth.assertThat((actualResults as Result.Success).data).isEqualTo(expectedResult)
    }



}