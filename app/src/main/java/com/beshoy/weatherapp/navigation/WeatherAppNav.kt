package com.beshoy.weatherapp.navigation

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.beshoy.weatherapp.ui.home.HomeScreen
import com.beshoy.weatherapp.ui.home.HomeViewModel
import java.util.Locale

@Composable
fun WeatherAppNav(
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            val context = LocalContext.current
            val homeViewModel: HomeViewModel = hiltViewModel()
            val state by homeViewModel.state.collectAsStateWithLifecycle()
            LaunchedEffect(key1 = state.defaultLocation) {
                context.getCityName(
                    latitude = state.defaultLocation.latitude,
                    longitude = state.defaultLocation.longitude
                ) { address ->
                    val cityName = address.locality
                    homeViewModel.setCityName(cityName)
                }
            }
            HomeScreen(
                state = state,
                onTryAgainClicked = homeViewModel::loadWeatherData,
                onSetTempUnit = homeViewModel::setUnit
            )
        }
    }

}

fun Context.getCityName(longitude: Double, latitude: Double, onAddressReceived: (Address) -> Unit) {
    val geoCoder = Geocoder(this, Locale.getDefault())

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        geoCoder.getFromLocation(latitude, longitude, 1) { addresses ->
            if (addresses.isNotEmpty()) {
                onAddressReceived(addresses[0])
            }
        }
    } else {
        val addresses = geoCoder.getFromLocation(latitude, longitude, 1)
        if (addresses?.isNotEmpty() == true) {
            onAddressReceived(addresses[0])
        }
    }
}