package com.fittrackapp.fittrack_mobile.presentation

import com.fittrackapp.fittrack_mobile.data.local.entity.ActivityEntity
import com.fittrackapp.fittrack_mobile.data.local.entity.ActivitySummaryEntity
import com.fittrackapp.fittrack_mobile.utils.getEndOfPeriod
import com.fittrackapp.fittrack_mobile.utils.getStartAndEndOfPeriod
import com.fittrackapp.fittrack_mobile.utils.getStartOfPeriod
import java.time.Period

enum class SelectedPeriod {
    TODAY,
    THIS_WEEK,
    THIS_MONTH,
    THIS_YEAR,
    ALL_TIME,
}

data class SummaryViewState(
    val selectedPeriod: SelectedPeriod = SelectedPeriod.THIS_YEAR,
    val filteredActivities: List<List<ActivitySummaryEntity>> = emptyList(),
) {
    val selectedStartTime: Long
        get() = selectedPeriod.getStartOfPeriod()

    val selectedEndTime: Long
        get() = selectedPeriod.getEndOfPeriod()

    val totalSteps: Int
        get() = filteredActivities.sumOf { it.sumOf { activity -> activity.steps } }

    val totalDistance: Double
        get() = filteredActivities.sumOf { it.sumOf { activity -> activity.distance / 1000 } }

    val totalCalories: Int
        get() = filteredActivities.sumOf { it.sumOf { activity -> activity.caloriesBurned.toInt() } }

}
