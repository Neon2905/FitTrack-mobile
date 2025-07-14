package com.fittrackapp.fittrack_mobile.domain.model

import android.location.Location
import java.util.Date

data class Activity(
    val id: Int = 0,
    val type: String,
    val startTime: Date,
    val endTime: Date? = null,
    val steps: Int = 0,
    val distance: Double = 0.0,
    val duration: Long,
    val calories: Double,
    val reps: Int = 0,
    val challengeId: Int? = null,
    val tracks: List<Location> = emptyList(),
)
