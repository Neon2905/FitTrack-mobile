package com.fittrackapp.fittrack_mobile.presentation

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import com.fittrackapp.fittrack_mobile.data.local.dao.ActivityDao
import com.fittrackapp.fittrack_mobile.data.local.dao.DailySummaryDao
import com.fittrackapp.fittrack_mobile.data.local.entity.DailySummaryEntity
import com.fittrackapp.fittrack_mobile.data.local.prefs.PrefKeys
import com.fittrackapp.fittrack_mobile.domain.model.Activity
import com.fittrackapp.fittrack_mobile.utils.format
import com.fittrackapp.fittrack_mobile.utils.formatToDate
import com.fittrackapp.fittrack_mobile.utils.getStartAndEndOfDay
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class StatisticsViewModel @Inject constructor(
    private val dailySummaryDao: DailySummaryDao,
    private val activityDao: ActivityDao
) : ViewModel() {
    private val _state = MutableStateFlow(
        StatisticsViewState()
    )
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            dailySummaryDao.getAll()
                .collect { summaries ->
                    _state.update {
                        it.copy(
                            dailySummaries = it.dailySummaries + summaries
                        )
                    }
                }
        }
        viewModelScope.launch {
            // Get Today's Summary
            val (start, end) = Date().getStartAndEndOfDay()
            activityDao.getAllByTime(start, end).collect { todayActivities ->
                val todaySummary = if (todayActivities.isEmpty()) {
                    DailySummaryEntity(
                        date = Date().format("yyyy-MM-dd")
                    )
                } else {
                    DailySummaryEntity(
                        date = Date().format("yyyy-MM-dd"),
                        totalCalories = todayActivities.sumOf { activity -> activity.caloriesBurned },
                        totalSteps = todayActivities.sumOf { activity -> activity.steps },
                        totalDistance = todayActivities.sumOf { activity -> activity.distance },
                        totalDuration = todayActivities.sumOf { activity -> activity.duration },
                        totalActivities = todayActivities.size,
                        totalChallenges = todayActivities.count { activity -> activity.challengeId != null },
                        isUploaded = false
                    )
                }
                _state.update {
                    it.copy(
                        todaySummary = todaySummary,
                        currentSummary = todaySummary
                    )
                }
            }
        }
    }

    fun onActivitySelected(summary: DailySummaryEntity) {
        _state.value = _state.value.copy(currentSummary = summary)
        fetchActivitiesForSelectedDate()
    }

    fun fetchActivitiesForSelectedDate() {
        val date: Date? = _state.value.currentSummary?.date?.formatToDate("yyyy-MM-dd")
        if (date == null) return
        viewModelScope.launch {
            val (start, end) = date.getStartAndEndOfDay()
            activityDao.getSummaryByTime(start, end).collect { activities ->
                _state.update {
                    it.copy(
                        currentSummaryActivities = activities,
                    )
                }
            }
        }
    }
}