package com.fittrackapp.fittrack_mobile.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fittrackapp.fittrack_mobile.data.local.prefs.PrefKeys

@Entity(tableName = "daily_summary")
data class DailySummaryEntity(
    @PrimaryKey
    val date: String,
    val totalCalories: Double = 0.0,
    val targetCalorie: Double = PrefKeys.dailyCalorieGoal,
    val totalSteps: Int = 0,
    val totalDistance: Double = 0.0,
    val totalDuration: Long = 0L,
    val totalActivities: Int = 0,
    val totalChallenges: Int = 0,
    val isUploaded: Boolean = false,
)