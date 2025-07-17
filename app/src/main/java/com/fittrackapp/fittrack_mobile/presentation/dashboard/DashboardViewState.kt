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
            if (activity.startTime.hours == hour) (activity.distance) else 0.0
        }.toFloat()
    }

    val totalDistance: Double
        get() = activities.sumOf { it.distance }

    val totalCalories: Int
        get() = activities.sumOf { it.caloriesBurned }.toInt()

    private val lastWeekAverageCalorie: Double
        get() =
            if (lastWeekActivities.isNotEmpty())
                (lastWeekActivities.sumOf { it.caloriesBurned } / lastWeekActivities.size.toDouble())
            else
                0.0

    private val todayAverageCalorie: Double
        get() =
            if (activities.isNotEmpty())
                (activities.sumOf { it.caloriesBurned } / activities.size.toDouble())
            else
                0.0

    val averageCalorieBurned: Int
        get() = (todayAverageCalorie + lastWeekAverageCalorie).toInt() / 2

    val isCalorieTrendUp = averageCalorieBurned >= lastWeekAverageCalorie

    private val lastWeekAverageDistance: Double
        get() =
            if (lastWeekActivities.isNotEmpty())
                lastWeekActivities.sumOf { it.distance } / lastWeekActivities.size.toDouble()
            else
                0.0

    private val todayAverageDistance: Double
        get() =
            if (activities.isNotEmpty())
                activities.sumOf { it.distance } / activities.size.toDouble()
            else 0.0

    val averageDistance: Double
        get() = (todayAverageDistance + lastWeekAverageDistance) / 2.0

    val isDistanceTrendUp = averageDistance >= lastWeekAverageDistance

    private val lastWeekAverageSteps: Double
        get() =
            if (lastWeekActivities.isNotEmpty())
                lastWeekActivities.sumOf { it.steps } / lastWeekActivities.size.toDouble()
            else
                0.0

    private val todayAverageSteps: Double
        get() =
            if (activities.isNotEmpty())
                activities.sumOf { it.steps } / activities.size.toDouble()
            else 0.0

    val averageSteps: Int
        get() = (todayAverageSteps + lastWeekAverageSteps).toInt() / 2

    val isStepsTrendUp = averageSteps >= lastWeekAverageSteps
}