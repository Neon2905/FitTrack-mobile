package com.fittrackapp.fittrack_mobile.presentation.register

import android.location.Location
import com.fittrackapp.fittrack_mobile.data.local.entity.ActivityType
import com.fittrackapp.fittrack_mobile.utils.CalorieUtils
import com.fittrackapp.fittrack_mobile.utils.predictActivityType
import java.util.Date
import kotlin.time.Duration

data class RegisterLiveActivityViewState(
    val isLive: Boolean = false,
    val onPause: Boolean = false,
    val isLoading: Boolean = false,
    val steps: Int = 0,
    val currentLocation: Location? = null,
    val distance: Double = 0.0, // meters
    val duration: Duration = Duration.ZERO,
    val targetType: String? = null,
    val targetValue: Double = 0.0,
    val tracks: List<List<Location>> = emptyList(), // List of tracks
    val errorMessage: String? = null,
    val calories: Double = CalorieUtils.calculateCalories(steps, distance, duration),
    val isTargetReached: Boolean = false,
    val showResultModal: Boolean = false,
    val startTime: Date? = null,
    val isSaving: Boolean = false,
) {
    val activityType: ActivityType
        get() =
            predictActivityType(distance, duration)
}