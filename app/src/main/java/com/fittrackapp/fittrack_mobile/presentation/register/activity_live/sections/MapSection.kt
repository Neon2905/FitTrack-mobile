package com.fittrackapp.fittrack_mobile.presentation.register.activity_live

import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Layers
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fittrackapp.fittrack_mobile.navigation.Navigator
import com.fittrackapp.fittrack_mobile.presentation.register.RegisterLiveActivityViewModel
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun MapSection(
    viewModel: RegisterLiveActivityViewModel,
    modifier: Modifier = Modifier,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val canRender = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.fetchInitialLocation()
        canRender.value = true
    }

    if (canRender.value) {
        // Map showing the tracks
        val allPoints = state.tracks.flatten()
        val defaultLatLng =
            state.currentLocation?.let { LatLng(it.latitude, it.longitude) } ?: LatLng(
                0.0,
                0.0
            )
        val cameraLatLng =
            allPoints.firstOrNull()?.let { LatLng(it.latitude, it.longitude) } ?: defaultLatLng
        val cameraPositionState = rememberCameraPositionState {
            position = com.google.android.gms.maps.model.CameraPosition.fromLatLngZoom(
                cameraLatLng, 16f
            )
        }
        val mapTypeState = remember { mutableStateOf(MapType.NORMAL) }

        Box(modifier = modifier.fillMaxSize()) {
            GoogleMap(
                modifier = Modifier.matchParentSize(),
                cameraPositionState = cameraPositionState,
                properties = MapProperties(
                    isMyLocationEnabled = true,
                    mapType = mapTypeState.value
                ),
                uiSettings = MapUiSettings(
                    myLocationButtonEnabled = true
                )
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
            FloatingActionButton(
                onClick = {
                    mapTypeState.value =
                        if (mapTypeState.value == MapType.NORMAL)
                            MapType.SATELLITE
                        else
                            MapType.NORMAL
                },
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(start = 15.dp, top = 60.dp)
                    .size(45.dp),
                shape = MaterialTheme.shapes.small,
                containerColor = MaterialTheme.colorScheme.onBackground,
            ) {
                Icon(
                    contentDescription = "Change Layer",
                    imageVector = Icons.Default.Layers,
                    tint = MaterialTheme.colorScheme.onPrimary,
                )
            }
        }
    }
}