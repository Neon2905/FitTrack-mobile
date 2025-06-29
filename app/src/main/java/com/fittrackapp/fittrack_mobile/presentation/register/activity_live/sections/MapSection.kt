package com.fittrackapp.fittrack_mobile.presentation.register.activity_live

import android.location.Location
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun MapSection(tracks: List<List<Location>>, currentLocation: Location?) {
    // Map showing the tracks
    val allPoints = tracks.flatten()
    val defaultLatLng = currentLocation ?: LatLng(0.0, 0.0)
    val cameraLatLng =
        allPoints.firstOrNull()?.let { LatLng(it.latitude, it.longitude) } ?: defaultLatLng
    val cameraPositionState = rememberCameraPositionState {
        position = com.google.android.gms.maps.model.CameraPosition.fromLatLngZoom(
            cameraLatLng as LatLng, 16f
        )
    }
    GoogleMap(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(3f / 5f),
        cameraPositionState = cameraPositionState,
        properties = com.google.maps.android.compose.MapProperties(isMyLocationEnabled = true),
        uiSettings = com.google.maps.android.compose.MapUiSettings(myLocationButtonEnabled = true)
    ) {
        tracks.forEach { track ->
            if (track.size > 1) {
                Polyline(
                    points = track.map { LatLng(it.latitude, it.longitude) },
                    color = MaterialTheme.colorScheme.primary,
                    width = 6f
                )
            }
        }
    }
}