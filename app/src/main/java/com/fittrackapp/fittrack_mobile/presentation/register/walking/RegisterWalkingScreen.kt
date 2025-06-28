package com.fittrackapp.fittrack_mobile.presentation.register

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.android.gms.maps.model.LatLng

@Preview
@Composable
fun RegisterWalkingScreen() {
    val viewModel: RegisterWalkingViewModel = hiltViewModel()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        Log.i("RegisterWalkingScreen", "Fetching initial location")
        viewModel.fetchInitialLocation()

        Log.i("RegisterWalkingScreen", "Fetching initial location completed. ${state.currentLocation}")
    }



    Log.i("RegisterWalkingScreen", "Start of rendering: ${state.currentLocation}")

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Steps: ${state.steps}")
        Text("Distance: ${"%.2f".format(state.distance / 1000)} km")
        Text("Duration: ${state.duration.inWholeSeconds} s")
        Text("Target: ${state.targetDuration.inWholeSeconds} s")
        Text("Tracks: ${state.tracks.size}")

        Spacer(modifier = Modifier.height(16.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Target: ${(state.targetDuration.inWholeMinutes)} min")
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = { viewModel.increaseTargetDuration() }) { Text("+") }
            Spacer(modifier = Modifier.width(4.dp))
            Button(onClick = { viewModel.decreaseTargetDuration() }) { Text("-") }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (!state.isLive) {
            Button(onClick = viewModel::start) {
                Text("Start")
            }
        } else {
            Row {
                if (state.onPause) {
                    Button(onClick = viewModel::start) {
                        Text("Resume")
                    }
                } else {
                    Button(onClick = viewModel::pause) {
                        Text("Pause")
                    }
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = viewModel::stop) {
                    Text("Stop")
                }
            }
        }

        if (state.errorMessage != null) {
            Text("Error: ${state.errorMessage}", color = MaterialTheme.colorScheme.error)
        }

        // Map showing the tracks
        val allPoints = state.tracks.flatten()
        val defaultLatLng = state.currentLocation ?: LatLng(0.0, 0.0)
        val cameraLatLng = allPoints.firstOrNull()?.let { LatLng(it.latitude, it.longitude) } ?: defaultLatLng
        val cameraPositionState = rememberCameraPositionState {
            position = com.google.android.gms.maps.model.CameraPosition.fromLatLngZoom(
                cameraLatLng as LatLng, 16f
            )
        }
        GoogleMap(
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp),
            cameraPositionState = cameraPositionState,
            properties = com.google.maps.android.compose.MapProperties(isMyLocationEnabled = true),
            uiSettings = com.google.maps.android.compose.MapUiSettings(myLocationButtonEnabled = true)
        ) {
            state.tracks.forEach { track ->
                if (track.size > 1) {
                    Polyline(
                        points = track.map { LatLng(it.latitude, it.longitude) },
                        color = MaterialTheme.colorScheme.primary,
                        width = 6f
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

    }
}