package com.beshoy.weatherapp.ui.home

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beshoy.weatherapp.data.Result
import com.beshoy.weatherapp.data.remote.toResourceId
import com.beshoy.weatherapp.domain.model.DefaultLocation
import com.beshoy.weatherapp.domain.model.Units
import com.beshoy.weatherapp.domain.model.Weather
import com.beshoy.weatherapp.domain.usecase.GetWeatherUseCase
import com.beshoy.weatherapp.domain.usecase.settings.location.GetLocationUseCase
import com.beshoy.weatherapp.domain.usecase.settings.unit.GetUnitUseCase
import com.beshoy.weatherapp.domain.usecase.settings.unit.SetUnitUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getWeatherUseCase: GetWeatherUseCase,
    private val getLocationUseCase: GetLocationUseCase,
    private val getUnitUseCase: GetUnitUseCase,
    private val setUnitUseCase: SetUnitUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(HomeScreenViewState(isLoading = true))
    val state: StateFlow<HomeScreenViewState> = _state.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            combine(
                getLocationUseCase(),
                getUnitUseCase()
            ) { location, unit ->
                Pair(location, unit)
            }.collect { (location, unit) ->
                setHomeState { copy(defaultLocation = location, units = unit) }
                loadWeatherData(location, unit)
            }
        }
    }

    fun loadWeatherData(
        location: DefaultLocation = state.value.defaultLocation,
        unit: Units = state.value.units
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = getWeatherUseCase(location = location, days = 7, units = unit)
            processResult(result)
        }
    }

    private fun processResult(result: Result<Weather>) {
        when (result) {
            is Result.Error -> setHomeState {
                copy(
                    isLoading = false,
                    errorMessageId = result.errorType.toResourceId()
                )
            }

            Result.Loading -> setHomeState { copy(isLoading = true) }
            is Result.Success -> setHomeState {
                copy(
                    weather = result.data,
                    isLoading = false,
                    errorMessageId = null
                )
            }
        }
    }

    fun setCityName(cityName: String) {
        setHomeState { copy(cityName = cityName) }
    }

    fun setUnit(units: Units) {
        viewModelScope.launch(Dispatchers.IO) {
            setUnitUseCase(units)
        }
    }

    private fun setHomeState(stateReducer: HomeScreenViewState.() -> HomeScreenViewState) {
        viewModelScope.launch {
            _state.emit(stateReducer(state.value))
        }
    }
}

data class HomeScreenViewState(
    val defaultLocation: DefaultLocation = DefaultLocation(0.0, 0.0),
    val units: Units = Units.METRIC,
    val cityName: String = "-",
    val weather: Weather? = null,
    val isLoading: Boolean = false,
    @StringRes val errorMessageId: Int? = null
)
