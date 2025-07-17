package com.fittrackapp.fittrack_mobile.presentation.register

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.fittrackapp.fittrack_mobile.data.local.entity.ActivityType
import java.time.LocalDateTime
import java.util.Date
import kotlin.time.Duration

data class LogActivityViewState(
    val isSaving: Boolean = false,
    val activityType: ActivityType,
    val distance: Double = 0.0,
    val steps: Int = 0,
    val startTime: Date,
    val duration: Duration,
    val sets: Int = 0,
    val reps: Int = 0,
    val liftWeight: Double = 0.0,
    val laps: Int = 0,
    val poolLength: Double = 0.0,
)