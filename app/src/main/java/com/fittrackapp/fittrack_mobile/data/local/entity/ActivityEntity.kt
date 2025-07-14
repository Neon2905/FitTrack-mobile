package com.fittrackapp.fittrack_mobile.data.local.entity

import android.location.Location
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "activity")
data class ActivityEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val type: ActivityType,
    val startTime: Date,
    val endTime: Date? = null,
    val duration: Long, // Use Long for milliseconds
    val distance: Double = 0.0,
    val caloriesBurned: Double,
    val steps: Int = 0,
    val reps: Int = 0,
    val challengeId: Int? = null,
    val tracks: List<Location> = emptyList(), // List of tracks
    val isUploaded: Boolean = false,
)

enum class ActivityType {
    WALKING, RUNNING, CYCLING, WEIGHTLIFTING, UNKNOWN
}