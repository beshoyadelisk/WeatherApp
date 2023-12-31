package com.beshoy.weatherapp.repository.weather

import com.beshoy.weatherapp.data.ErrorType
import com.beshoy.weatherapp.data.Result
import com.beshoy.weatherapp.data.remote.WeatherAPI
import com.beshoy.weatherapp.data.remote.WeatherNetworkDataSource
import com.beshoy.weatherapp.data.remote.WeatherNetworkDataSourceImpl
import com.beshoy.weatherapp.data.repository.WeatherRepositoryImpl
import com.beshoy.weatherapp.domain.model.DefaultLocation
import com.beshoy.weatherapp.domain.model.Units
import com.beshoy.weatherapp.domain.repository.WeatherRepository
import com.google.common.truth.Truth
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Test
import retrofit2.Response
import java.io.IOException

class WeatherRepositoryUnitTest {

    @MockK
    val mockWeatherService = mockk<WeatherAPI>(relaxed = true)


    @Test
    fun fetchWeatherDataSuccessfullyThenSuccessfullyMappedResultEmitted() =
        runBlocking {
            coEvery {
                mockWeatherService.getForecast(any(), any(), any())
            } returns Response.success(
                fakeSuccessWeather
            )

            val weatherRepository = createWeatherRepository()

            val expectedResult = fakeSuccessMappedWeather

            val actualResults = weatherRepository.getForecast(
                location = DefaultLocation(0.0, 0.0),
                days = 1,
                units = Units.METRIC
            )
            Truth.assertThat(actualResults).isInstanceOf(Result.Success::class.java)
            Truth.assertThat((actualResults as Result.Success).data).isEqualTo(expectedResult)
        }

    @Test
    fun fetchWeatherDataThenServerErrorHappensThenAServerErrorIsEmitted() =
        runBlocking {
            coEvery {
                mockWeatherService.getForecast(
                    any(),
                    any(),
                    any()
                )
            } returns Response.error(
                500,
                "{}".toResponseBody()
            )

            val weatherRepository = createWeatherRepository()

            val actualResults = weatherRepository.getForecast(
                location = DefaultLocation(0.0, 0.0),
                days = 1,
                units = Units.METRIC

            )
            Truth.assertThat(actualResults).isInstanceOf(Result.Error::class.java)
            Truth.assertThat((actualResults as Result.Error).errorType).isEqualTo(ErrorType.SERVER)
        }


    @Test
    fun fetchWeatherDataThenClientErrorHappensThenAClientErrorIsEmitted() =
        runBlocking {
            coEvery {
                mockWeatherService.getForecast(
                    any(),
                    any(),
                    any(),
                )
            } returns Response.error(
                404,
                "{}".toResponseBody()
            )

            val weatherRepository = createWeatherRepository()

            val actualResults = weatherRepository.getForecast(
                location = DefaultLocation(0.0, 0.0),
                days = 1,
                units = Units.METRIC

            )

            Truth.assertThat(actualResults).isInstanceOf(Result.Error::class.java)
            Truth.assertThat((actualResults as Result.Error).errorType).isEqualTo(ErrorType.CLIENT)
        }

    @Test
    fun fetchWeatherDataAndAnUnauthorizedErrorHappensThenAnUnauthorizedErrorIsEmitted() =
        runBlocking {
            coEvery {
                mockWeatherService.getForecast(
                    any(),
                    any(),
                    any(),
                )
            } returns Response.error(
                401,
                "{}".toResponseBody()
            )

            val weatherRepository = createWeatherRepository()

            val actualResults = weatherRepository.getForecast(
                location = DefaultLocation(0.0, 0.0),
                days = 1,
                units = Units.METRIC

            )

            Truth.assertThat(actualResults).isInstanceOf(Result.Error::class.java)
            Truth.assertThat((actualResults as Result.Error).errorType)
                .isEqualTo(ErrorType.UNAUTHORIZED)
        }


    @Test
    fun fetchWeatherDataAndAGenericErrorHappensThenAGenericErrorIsEmitted() =
        runBlocking {
            coEvery {
                mockWeatherService.getForecast(
                    any(),
                    any(),
                    any(),
                )
            } returns Response.error(
                800,
                "{}".toResponseBody()
            )

            val weatherRepository = createWeatherRepository()

            val actualResults = weatherRepository.getForecast(
                location = DefaultLocation(0.0, 0.0),
                days = 1,
                units = Units.METRIC

            )

            Truth.assertThat(actualResults).isInstanceOf(Result.Error::class.java)
            Truth.assertThat((actualResults as Result.Error).errorType)
                .isEqualTo(ErrorType.GENERIC)
        }

    @Test
    fun fetchWeatherDataAndAnIOExceptionIsThrownThenAConnectionErrorIsEmitted() =
        runBlocking {
            coEvery {
                mockWeatherService.getForecast(
                    any(),
                    any(),
                    any()
                )
            } throws IOException()

            val weatherRepository = createWeatherRepository()

            val actualResults = weatherRepository.getForecast(
                location = DefaultLocation(0.0, 0.0),
                days = 1,
                units = Units.METRIC

            )

            Truth.assertThat(actualResults).isInstanceOf(Result.Error::class.java)
            Truth.assertThat((actualResults as Result.Error).errorType)
                .isEqualTo(ErrorType.IO_CONNECTION)
        }

    @Test
    fun fetchWeatherDataAndAnUnknownExceptionIsThrownThenAGenericErrorIsEmitted() =
        runBlocking {
            coEvery {
                mockWeatherService.getForecast(
                    any(),
                    any(),
                    any()
                )
            } throws Exception()

            val weatherRepository = createWeatherRepository()

            val actualResults = weatherRepository.getForecast(
                location = DefaultLocation(0.0, 0.0),
                days = 1,
                units = Units.METRIC

            )

            Truth.assertThat(actualResults).isInstanceOf(Result.Error::class.java)
            Truth.assertThat((actualResults as Result.Error).errorType)
                .isEqualTo(ErrorType.GENERIC)
        }

    private fun createWeatherRepository(
        weatherNetworkDataSource: WeatherNetworkDataSource = WeatherNetworkDataSourceImpl(
            weatherAPI = mockWeatherService
        )
    ): WeatherRepository = WeatherRepositoryImpl(
        weatherNetworkDataSource = weatherNetworkDataSource
    )
}
