package com.fittrackapp.fittrack_mobile.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "challenge")
data class ChallengeEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String? = null,
    val goalType: GoalType = GoalType.CALORIES,
    val targetValue: Double = 0.0,
    val dueDate: Date? = null,
    val startDate: Date? = Date(),
    val endDate: Date? = null,
    val progressValue: Double = 0.0,
    val isCompleted: Boolean = false,
    val isUploaded: Boolean = false,
)

enum class GoalType {
    DISTANCE, DURATION, STEPS, CALORIES
}
