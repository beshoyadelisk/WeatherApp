package com.beshoy.weatherapp.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil.compose.AsyncImage

@Composable
fun WeatherIcon(iconUrl: String, contentDescription: String, modifier: Modifier = Modifier) {
    AsyncImage(
        model = iconUrl,
        contentDescription = contentDescription,
        modifier = modifier,
        onError = {
            println("AsyncImageError ${it.result.throwable.message}")
        },
        onSuccess = {
            println("AsyncImageError ${it.result.request.data}")
        }
    )
}


