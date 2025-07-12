package com.fittrackapp.fittrack_mobile.presentation.dashboard

import com.fittrackapp.fittrack_mobile.data.local.entity.ActivityEntity
import com.fittrackapp.fittrack_mobile.domain.model.Activity
import com.fittrackapp.fittrack_mobile.domain.model.AuthUser
import kotlin.time.Duration.Companion.hours

data class DashboardViewState(
    val activities: List<ActivityEntity> = emptyList(),
    val lastWeekActivities: List<ActivityEntity> = emptyList(),
) {
    val totalSteps: Int
        get() = activities.sumOf { it.steps }

    val hourlyStepData: List<Float> = List(24) { hour ->
        activities.sumOf { activity ->
            if (activity.startTime.hours == hour) activity.steps else 0
        }.toFloat()
    }

    val hourlyDistanceData: List<Float> = List(24) { hour ->
        activities.sumOf { activity ->
            if (activity.startTime.hours == hour) (activity.distance / 1000).toInt() else 0
        }.toFloat()
    }

    val totalDistance: Double
        get() = activities.sumOf { it.distance / 1000 }

    val totalCalories: Int
        get() = activities.sumOf { it.caloriesBurned }.toInt()

    private val averageLastWeekCalorie: Int
        get() =
            if (lastWeekActivities.isNotEmpty())
                (lastWeekActivities.sumOf { it.caloriesBurned } / lastWeekActivities.size).toInt()
            else
                0

    val averageCalorieBurned: Double
        get() = activities.sumOf { it.caloriesBurned } + averageLastWeekCalorie / 2

    val isCalorieTrendUp = averageCalorieBurned >= averageLastWeekCalorie

    private val averageLastWeekDistance: Double
        get() =
            if (lastWeekActivities.isNotEmpty())
                lastWeekActivities.sumOf { it.distance } / lastWeekActivities.size
            else
                0.0

    val averageDistance: Double
        get() = activities.sumOf { it.distance } + averageLastWeekDistance / 2

    val isDistanceTrendUp = averageDistance >= averageLastWeekDistance

    private val averageLastWeekSteps: Int
        get() =
            if (lastWeekActivities.isNotEmpty())
                lastWeekActivities.sumOf { it.steps } / lastWeekActivities.size
            else
                0

    val averageSteps: Int
        get() = activities.sumOf { it.steps } + averageLastWeekSteps / 2

    val isStepsTrendUp = averageSteps >= averageLastWeekSteps
}