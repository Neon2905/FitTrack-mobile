package com.fittrackapp.fittrack_mobile.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class ActivityEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val userId: Int,
    val type: ActivityType,
    val startTime: String,
    val endTime: String,
    val duration: Double,
    val distance: Double,
    val caloriesBurned: Double,
    val steps: Int = 0,
    val reps: Int = 0,
    val challengeId: Int?
)

enum class ActivityType {
    WALKING, RUNNING, CYCLING, WEIGHTLIFTING
}