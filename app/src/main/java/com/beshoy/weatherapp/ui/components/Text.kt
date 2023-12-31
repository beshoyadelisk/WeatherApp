package com.beshoy.weatherapp.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.beshoy.weatherapp.R

@Composable
fun ErrorWithTryAgain(
    @StringRes errorMessageId: Int,
    modifier: Modifier,
    onTryAgainClicked: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = stringResource(id = errorMessageId),
            textAlign = TextAlign.Center,
            modifier = modifier.align(Alignment.CenterHorizontally),
            style = MaterialTheme.typography.bodyMedium
        )
        Button(
            onClick = { onTryAgainClicked() },
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Text(
                text = stringResource(R.string.try_again),
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun Headline(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        modifier = modifier,
        style = MaterialTheme.typography.headlineLarge,
        fontWeight = FontWeight.ExtraBold
    )
}

@Composable
fun TemperatureHeadline(temperature: String, modifier: Modifier = Modifier) {
    Headline(
        text = temperature,
        modifier = modifier
            .padding(horizontal = 16.dp)
            .padding(vertical = 8.dp)
    )
}

@Composable
fun Subtitle(
    text: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Start,
    color: Color = Color.Unspecified
) {
    Text(
        text = text,
        style = MaterialTheme.typography.headlineMedium,
        modifier = modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        color = color,
        textAlign = textAlign
    )
}

@Composable
fun Body(text: String, modifier: Modifier = Modifier, color: Color = Color.Unspecified) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyMedium,
        color = color,
        modifier = modifier
    )
}

@Composable
fun Temperature(text: String,modifier: Modifier = Modifier) {
    Body(
        text = text,
        modifier = modifier
    )
}

@Composable
fun ForecastedTime(text: String, modifier: Modifier = Modifier) {
    Body(
        text = text,
        modifier = modifier
    )
}

