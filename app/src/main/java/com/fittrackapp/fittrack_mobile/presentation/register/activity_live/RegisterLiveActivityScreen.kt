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
import com.fittrackapp.fittrack_mobile.presentation.register.activity_live.MapSection
import com.fittrackapp.fittrack_mobile.presentation.register.activity_live.sections.TargetSection
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.android.gms.maps.model.LatLng

@Preview
@Composable
fun RegisterLiveActivityScreen(viewModel: RegisterLiveActivityViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchInitialLocation()
    }

    Column(
        Modifier
            .padding(WindowInsets.systemBars.asPaddingValues()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        MapSection(tracks = state.tracks, currentLocation = state.currentLocation)

        TargetSection(
            target = state.target,
            onTypeChange = {},
            onTargetDecrease = {},
            onTargetIncrease = {}
        )

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
        Spacer(modifier = Modifier.height(16.dp))

    }
}