package com.beshoy.weatherapp.ui.components

import android.Manifest
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.beshoy.weatherapp.R
import com.beshoy.weatherapp.domain.model.Units
import com.beshoy.weatherapp.ui.theme.WeatherAppTheme

@Composable
fun PermissionRationaleDialog(
    isDialogShown: MutableState<Boolean>,
    activityPermissionResult: ActivityResultLauncher<String>,
    showWeatherUI: MutableState<Boolean>
) {
    AlertDialog(
        onDismissRequest = { isDialogShown.value = false },
        title = {
            Text(text = stringResource(R.string.location_permission_title))
        },
        text = {
            Text(text = stringResource(R.string.location_permission_description))
        },
        confirmButton = {
            Button(onClick = {
                isDialogShown.value = false
                activityPermissionResult.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
            }) {
                Text(text = stringResource(R.string.location_permission_button_grant))
            }
        },
        dismissButton = {
            Button(onClick = {
                isDialogShown.value = false
                showWeatherUI.value = false
            }) {
                Text(text = stringResource(R.string.location_permission_button_deny))
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun SettingOptionsDialogPreview() {
    WeatherAppTheme {
        SettingUnitsDialog(onDismiss = {}, initialUnit = Units.IMPERIAL, onConfirm = {})
    }
}

@Composable
fun SettingUnitsDialog(
    initialUnit: Units,
    onDismiss: () -> Unit,
    onConfirm: (Units) -> Unit
) {
    Dialog(onDismissRequest = { onDismiss() }) {
        var selectedUnit by remember(initialUnit) { mutableStateOf(initialUnit) }
        Column(
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(16.dp)
                )
        ) {
            Subtitle(
                text = stringResource(id = R.string.temp_unit),
                modifier = Modifier.fillMaxWidth()
            )
            Units.entries.forEach { unit ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth().selectable(
                        selected = (unit == selectedUnit),
                        onClick = {
                            selectedUnit = unit
                        }
                    )
                ) {
                    RadioButton(
                        selected = unit == selectedUnit,
                        onClick = {
                            selectedUnit = unit
                        },
                        modifier = Modifier.semantics { contentDescription = unit.tempLabel }
                    )
                    Text(text = "${unit.value} (${unit.tempLabel})")
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(
                    onClick = { onConfirm(selectedUnit) }
                ) {
                    Text(stringResource(id = R.string.confirm))
                }
            }
        }

    }
}

