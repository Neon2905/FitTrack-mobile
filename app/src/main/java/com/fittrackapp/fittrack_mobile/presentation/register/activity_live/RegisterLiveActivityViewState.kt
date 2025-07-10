package com.fittrackapp.fittrack_mobile.presentation.register

import android.location.Location
import com.fittrackapp.fittrack_mobile.utils.CalorieUtils
import java.util.Date
import kotlin.time.Duration

data class RegisterLiveActivityViewState(
    val isLive: Boolean = false,
    val onPause: Boolean = false,
    val steps: Int = 0,
    val currentLocation: Location? = null,
    val distance: Double = 0.0, // km
    val duration: Duration = Duration.ZERO,
    val targetType: String? = null,
    val targetValue: Double = 0.0,
    val tracks: List<List<Location>> = emptyList(), // List of tracks
    val errorMessage: String? = null,
    val calories: Double = CalorieUtils.calculateCalories(steps, distance, duration),
    val isTargetReached: Boolean = false,
    val startTime: Date? = null,
)