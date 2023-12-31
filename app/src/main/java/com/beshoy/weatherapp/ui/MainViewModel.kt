package com.beshoy.weatherapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beshoy.weatherapp.domain.model.DefaultLocation
import com.beshoy.weatherapp.domain.usecase.settings.location.SetLocationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val setLocationUseCase: SetLocationUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(MainViewState())
    val state: StateFlow<MainViewState> = _state.asStateFlow()

    fun isPermissionGranted(isGranted: Boolean) {
        setMainState { copy(isPermissionGranted = isGranted) }
    }

    fun isLocationEnabled(enabled: Boolean) {
        setMainState { copy(isLocationSettingEnabled = enabled) }

    }

    fun setLocation(longitude: Double, latitude: Double) {
        viewModelScope.launch {
            setLocationUseCase(
                DefaultLocation(
                    longitude = longitude,
                    latitude = latitude
                )
            )
        }
    }

    private fun setMainState(stateReducer: MainViewState.() -> MainViewState) {
        viewModelScope.launch {
            _state.emit(stateReducer(state.value))
        }
    }
}

data class MainViewState(
    val isPermissionGranted: Boolean = false,
    val isLocationSettingEnabled: Boolean = false
)