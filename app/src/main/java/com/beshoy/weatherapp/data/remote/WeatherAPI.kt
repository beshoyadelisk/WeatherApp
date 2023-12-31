package com.beshoy.weatherapp.data.remote

import com.beshoy.weatherapp.BuildConfig
import com.beshoy.weatherapp.data.Result
import com.beshoy.weatherapp.data.remote.dto.WeatherDto
import com.beshoy.weatherapp.data.remote.dto.asExternalModel
import com.beshoy.weatherapp.domain.model.DefaultLocation
import com.beshoy.weatherapp.domain.model.Units
import com.beshoy.weatherapp.domain.model.Weather
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Inject
import javax.inject.Singleton

interface WeatherAPI {
    @GET("forecast.json")
    suspend fun getForecast(
        @Query("key") key: String = BuildConfig.WEATHER_API_KEY,
        @Query("q") location: String,
        @Query("days") days: Int
    ): Response<WeatherDto>
}

@Singleton
class WeatherNetworkDataSourceImpl @Inject constructor(
    private val weatherAPI: WeatherAPI
) : WeatherNetworkDataSource {
    override suspend fun getForecast(location: DefaultLocation, days: Int,units: Units): Result<Weather> =
        try {
            val response = weatherAPI.getForecast(location = location.toString(), days = days)
            if (response.isSuccessful && response.body() != null) {
                val weatherData = response.body()!!.asExternalModel(units)
                Result.Success(data = weatherData)
            } else {
                val throwable = mapResponseCodeToThrowable(response.code())
                throw throwable
            }
        } catch (ex: Exception) {
            throw ex
        }


}