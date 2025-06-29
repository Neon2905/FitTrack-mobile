package com.fittrackapp.fittrack_mobile.presentation.register

import android.location.Location
import kotlin.time.Duration

data class RegisterLiveActivityViewState(
    val isLive: Boolean = false,
    val onPause: Boolean = false,
    val steps: Int = 0,
    val currentLocation: Location? = null,
    val distance: Double = 0.0, // meters
    val duration: Duration = Duration.ZERO,
    val target: String? = null,
    val targetDuration: Duration = Duration.ZERO,
    val tracks: List<List<Location>> = emptyList(), // List of tracks
    val errorMessage: String? = null
)