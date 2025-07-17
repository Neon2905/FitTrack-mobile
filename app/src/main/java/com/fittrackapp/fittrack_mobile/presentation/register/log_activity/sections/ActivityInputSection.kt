package com.fittrackapp.fittrack_mobile.presentation.register.log_activity

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fittrackapp.fittrack_mobile.data.local.entity.ActivityType
import com.fittrackapp.fittrack_mobile.presentation.DateTimeInput
import com.fittrackapp.fittrack_mobile.presentation.register.LogActivityViewModel
import com.fittrackapp.fittrack_mobile.utils.toFormattedString
import java.time.ZoneId.systemDefault
import java.util.Date

@Composable
fun ActivityInputSection(
    viewModel: LogActivityViewModel,
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        // Date and Time Picker
        DateTimeInput(
            dateTime = state.startTime.toInstant()
                .atZone(systemDefault())
                .toLocalDateTime(),
            onChange = { startTime ->
                viewModel.onStartTimeChanged(
                    Date.from(startTime.atZone(systemDefault()).toInstant())
                )
            }
        )

        OutlinedTextField(
            value = state.duration.inWholeMinutes.toString(),
            onValueChange = viewModel::onDurationChanged,
            label = { Text("Duration (minutes)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        // Conditional inputs
        when (state.activityType) {

            ActivityType.WALKING -> {
                OutlinedTextField(
                    value = state.steps.toString(),
                    onValueChange = viewModel::onStepsChanged,
                    label = { Text("Steps") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                OutlinedTextField(
                    value = state.distance.toString(),
                    onValueChange = viewModel::onDistanceChanged,
                    label = { Text("Distance (km)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                )
            }

            ActivityType.RUNNING, ActivityType.CYCLING -> {
                OutlinedTextField(
                    value = state.distance.toString(),
                    onValueChange = viewModel::onDistanceChanged,
                    label = { Text("Distance (km)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                )
            }

            ActivityType.SWIMMING -> {
                OutlinedTextField(
                    value = state.laps.toString(),
                    onValueChange = viewModel::onLapsChanged,
                    label = { Text("Laps") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                OutlinedTextField(
                    value = state.poolLength.toString(),
                    onValueChange = viewModel::onPoolLengthChanged,
                    label = { Text("Pool Length (meters)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }

            ActivityType.WEIGHTLIFTING -> {
                OutlinedTextField(
                    value = state.sets.toString(),
                    onValueChange = viewModel::onSetsChanged,
                    label = { Text("Sets") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                OutlinedTextField(
                    value = state.reps.toString(),
                    onValueChange = viewModel::onRepsChanged,
                    label = { Text("Reps per Set") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                OutlinedTextField(
                    value = state.liftWeight.toString(),
                    onValueChange = viewModel::onLiftWeightChanged,
                    label = { Text("Weight (kg)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                )
            }

            else -> {
                // No additional fields for other activity types
            }
        }
    }
}
