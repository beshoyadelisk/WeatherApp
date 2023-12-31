package com.beshoy.weatherapp.ui

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.beshoy.weatherapp.navigation.WeatherAppNav
import com.beshoy.weatherapp.ui.components.EnableLocationSettingScreen
import com.beshoy.weatherapp.ui.components.LoadingScreen
import com.beshoy.weatherapp.ui.components.PermissionRationaleDialog
import com.beshoy.weatherapp.ui.components.RequiresPermissionsScreen
import com.beshoy.weatherapp.ui.theme.WeatherAppTheme
import com.beshoy.weatherapp.util.getLocationRequest
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val mainViewModel: MainViewModel by viewModels()
    private val locationRequestLauncher =
        registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                mainViewModel.isLocationEnabled(true)
            } else {
                mainViewModel.isLocationEnabled(false)
            }
        }
    private val permissionRequestLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            mainViewModel.isPermissionGranted(isGranted)
        }
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getLocationRequest(
            activity = this@MainActivity,
            locationLauncher = locationRequestLauncher
        ) {
            mainViewModel.isLocationEnabled(true)
        }
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        setContent {
            WeatherAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val state by mainViewModel.state.collectAsStateWithLifecycle()

                    CheckForPermissions(
                        onPermissionGranted = {
                            mainViewModel.isPermissionGranted(true)
                        },
                        onPermissionDenied = {
                            OnPermissionDenied(activityPermissionResult = permissionRequestLauncher)
                        }
                    )
                    MainScreen(state)

                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    @Composable
    fun MainScreen(state: MainViewState) {
        when {
            state.isLocationSettingEnabled && state.isPermissionGranted -> {
                fusedLocationProviderClient.lastLocation
                    .addOnSuccessListener { location: Location? ->
                        location?.run {
                            mainViewModel.setLocation(
                                longitude = location.longitude,
                                latitude = location.latitude
                            )
                        }
                    }
                WeatherAppNav(navController = rememberNavController())

            }

            state.isLocationSettingEnabled && !state.isPermissionGranted -> RequiresPermissionsScreen()

            !state.isLocationSettingEnabled && !state.isPermissionGranted -> EnableLocationSettingScreen()

            else -> LoadingScreen()
        }
    }

}


@Composable
fun Activity.OnPermissionDenied(
    activityPermissionResult: ActivityResultLauncher<String>,
) {
    val showWeatherUI = remember { mutableStateOf(false) }
    if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION)) {
        val isDialogShown = remember { mutableStateOf(true) }
        if (isDialogShown.value) {
            PermissionRationaleDialog(
                isDialogShown,
                activityPermissionResult,
                showWeatherUI
            )
        }
    } else {
        activityPermissionResult.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
    }
}

@Composable
fun Context.CheckForPermissions(
    onPermissionGranted: @Composable () -> Unit,
    onPermissionDenied: @Composable () -> Unit
) {
    when (ContextCompat.checkSelfPermission(
        this,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )) {
        PackageManager.PERMISSION_GRANTED -> {
            onPermissionGranted()
        }

        PackageManager.PERMISSION_DENIED -> {
            onPermissionDenied()
        }
    }
}
