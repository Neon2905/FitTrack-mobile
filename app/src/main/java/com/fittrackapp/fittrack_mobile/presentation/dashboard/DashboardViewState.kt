package com.fittrackapp.fittrack_mobile.presentation.dashboard

import com.fittrackapp.fittrack_mobile.data.local.entity.ActivityEntity
import com.fittrackapp.fittrack_mobile.domain.model.Activity
import com.fittrackapp.fittrack_mobile.domain.model.AuthUser

data class DashboardViewState(
    val activities: List<ActivityEntity> = emptyList(),
    val currentActivity: ActivityEntity? = activities.getOrNull(0),
) {
    val totalSteps: Int
        get() = activities.sumOf { it.steps }

    val totalDistance: Double
        get() = activities.sumOf { it.distance / 1000 }

    val totalCaloriesBurned: Double
        get() = activities.sumOf { it.caloriesBurned }
}