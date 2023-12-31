package com.beshoy.weatherapp.ui.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.beshoy.weatherapp.R
import com.beshoy.weatherapp.domain.model.CurrentWeather
import com.beshoy.weatherapp.domain.model.ForecastDay
import com.beshoy.weatherapp.domain.model.Units
import com.beshoy.weatherapp.domain.model.WeatherHour
import com.beshoy.weatherapp.domain.model.fakeSuccessMappedWeatherResponse
import com.beshoy.weatherapp.ui.components.ErrorWithTryAgain
import com.beshoy.weatherapp.ui.components.ForecastedTime
import com.beshoy.weatherapp.ui.components.LoadingScreen
import com.beshoy.weatherapp.ui.components.SettingUnitsDialog
import com.beshoy.weatherapp.ui.components.Subtitle
import com.beshoy.weatherapp.ui.components.Temperature
import com.beshoy.weatherapp.ui.components.TemperatureHeadline
import com.beshoy.weatherapp.ui.components.WeatherIcon
import com.beshoy.weatherapp.ui.theme.WeatherAppTheme
import java.time.Instant
import java.time.ZoneId

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    WeatherAppTheme {


        HomeScreen(
            state = HomeScreenViewState(weather = fakeSuccessMappedWeatherResponse),
            onTryAgainClicked = {},
            onSetTempUnit = {},
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    state: HomeScreenViewState,
    onSetTempUnit: (Units) -> Unit,
    onTryAgainClicked: () -> Unit
) {
    var showSettingsDialog by remember {
        mutableStateOf(false)
    }
    if (showSettingsDialog) {
        SettingUnitsDialog(
            initialUnit = state.units,
            onDismiss = { showSettingsDialog = false },
            onConfirm = {
                onSetTempUnit(it)
                showSettingsDialog = false
            }
        )
    }
    Scaffold(topBar = {
        TopAppBar(title = {
            Text(
                text = state.cityName,
                style = MaterialTheme.typography.headlineLarge
            )
        },
            actions = {
                Image(
                    imageVector = Icons.Outlined.Settings,
                    contentDescription = stringResource(R.string.settings),
                    modifier = Modifier
                        .padding(8.dp)
                        .clickable { showSettingsDialog = true }
                )
            }
        )
    }) { scaffoldPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(scaffoldPadding)
        ) {


            if (state.isLoading) {
                LoadingScreen()
            }

            if (state.errorMessageId != null) {
                ErrorScreen(state.errorMessageId, onTryAgainClicked)
            }

            state.weather?.currentWeather?.let { currentWeather ->
                CurrentWeatherWidget(
                    currentWeather = currentWeather,
                    tempLabel = state.units.tempLabel
                )
            }
            state.weather?.forecast?.firstOrNull()?.let { forecast ->
                HourlyWeatherWidget(
                    hourlyWeatherList = forecast.weatherHours,
                    tempLabel = state.units.tempLabel
                )
            }
            state.weather?.forecast?.let { dailyWeather ->
                DailyWeatherWidget(
                    forecastDayList = dailyWeather,
                    tempLabel = state.units.tempLabel
                )
            }
        }
    }
}

@Composable
private fun ColumnScope.ErrorScreen(errorMsgId: Int, onTryAgainClicked: () -> Unit) {
    Spacer(modifier = Modifier.weight(0.5f))
    ErrorWithTryAgain(
        errorMessageId = errorMsgId,
        modifier = Modifier.padding(16.dp),
        onTryAgainClicked = onTryAgainClicked
    )
    Spacer(modifier = Modifier.Companion.weight(0.5f))
}

@Composable
private fun CurrentWeatherWidget(currentWeather: CurrentWeather, tempLabel: String) {
    Column {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Subtitle(text = currentWeather.condition.conditionText)
            TemperatureHeadline(temperature = "${currentWeather.temp} $tempLabel")
        }


        Subtitle(
            text = stringResource(
                id = R.string.feels_like,
                currentWeather.feelLike
            ) + " " + tempLabel,
            color = MaterialTheme.colorScheme.secondary
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun HourlyWeatherWidget(hourlyWeatherList: List<WeatherHour>, tempLabel: String) {
    Subtitle(text = stringResource(id = R.string.today_forecast))

    LazyRow(modifier = Modifier.padding(16.dp)) {
        items(hourlyWeatherList, key = { it.time }) { hourlyWeather ->
            HourlyWeatherRow(
                weatherHour = hourlyWeather,
                tempLabel = tempLabel,
                modifier = Modifier
                    .animateItemPlacement()
                    .padding(4.dp)
            )
        }
    }
}

@Composable
private fun HourlyWeatherRow(weatherHour: WeatherHour, modifier: Modifier, tempLabel: String) {
    Card(modifier = modifier) {
        Row(modifier = Modifier.padding(4.dp)) {
            WeatherIcon(
                iconUrl = weatherHour.condition.icon,
                contentDescription = weatherHour.condition.conditionText,
                modifier = Modifier
                    .size(40.dp)
                    .align(Alignment.CenterVertically),
            )
            Column(
                modifier = Modifier
                    .padding(4.dp)
                    .align(Alignment.CenterVertically)
            ) {
                Temperature(text = "${weatherHour.temp}$tempLabel")
                ForecastedTime(text = weatherHour.time.split(" ")[1])
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun DailyWeatherWidget(forecastDayList: List<ForecastDay>, tempLabel: String) {
    Subtitle(text = stringResource(id = R.string.weekly_forecast))

    LazyColumn(modifier = Modifier.padding(16.dp)) {
        items(forecastDayList, key = { it.date }) { dailyWeather ->
            DailyWeatherRow(
                forecastDay = dailyWeather,
                tempLabel = tempLabel,
                modifier = Modifier.animateItemPlacement()
            )
        }
    }
}

@Composable
private fun DailyWeatherRow(forecastDay: ForecastDay, modifier: Modifier, tempLabel: String) {
    Row(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        WeatherIcon(
            iconUrl = forecastDay.day.condition.icon,
            contentDescription = forecastDay.day.condition.conditionText,
            modifier = Modifier
                .size(64.dp)
                .padding(4.dp)
                .align(Alignment.CenterVertically),
        )
        ForecastedTime(
            text = Instant.ofEpochSecond(forecastDay.dateEpoch.toLong())
                .atZone(ZoneId.systemDefault()).dayOfWeek.name,
            modifier = Modifier
                .align(Alignment.CenterVertically)
        )
        Spacer(modifier = Modifier.weight(1.0f))
        Column(modifier = Modifier.align(Alignment.CenterVertically)) {
            Temperature(
                text = stringResource(
                    id = R.string.max_temp,
                    forecastDay.day.maxTemp
                ) + " " + tempLabel
            )
            Temperature(
                text = stringResource(
                    id = R.string.min_temp,
                    forecastDay.day.minTemp
                ) + " " + tempLabel
            )
        }
    }
}
