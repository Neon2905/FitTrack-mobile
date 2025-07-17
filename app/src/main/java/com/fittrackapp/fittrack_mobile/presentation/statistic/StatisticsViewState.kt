package com.fittrackapp.fittrack_mobile.presentation

import com.fittrackapp.fittrack_mobile.data.local.entity.ActivitySummaryEntity
import com.fittrackapp.fittrack_mobile.data.local.entity.DailySummaryEntity
import com.fittrackapp.fittrack_mobile.domain.model.Activity

data class StatisticsViewState(
    val dailySummaries: List<DailySummaryEntity> = emptyList(),
    val currentSummary: DailySummaryEntity? = null,
    val currentSummaryActivities: List<ActivitySummaryEntity> = emptyList(),
    val todaySummary: DailySummaryEntity? = null
) {
    val totalSteps: Int
        get() = currentSummaryActivities.sumOf { it.steps }

    val hourlyStepData: List<Float> = List(24) { hour ->
        currentSummaryActivities.sumOf { activity ->
            if (activity.startTime.hours == hour) activity.steps else 0
        }.toFloat()
    }

    val hourlyDistanceData: List<Float> = List(24) { hour ->
        currentSummaryActivities.sumOf { activity ->
            if (activity.startTime.hours == hour) (activity.distance) else 0.0
        }.toFloat()
    }

    val totalDistance: Double
        get() = currentSummaryActivities.sumOf { it.distance }
}