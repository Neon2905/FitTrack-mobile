package com.fittrackapp.fittrack_mobile.data.local.entity

import android.location.Location
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "activity")
data class ActivitySummaryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val startTime: Date,
    val distance: Double = 0.0,
    val caloriesBurned: Double,
    val steps: Int = 0,
)