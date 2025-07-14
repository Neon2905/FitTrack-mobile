package com.fittrackapp.fittrack_mobile.presentation

import androidx.lifecycle.ViewModel
import com.fittrackapp.fittrack_mobile.ActivityRecognitionManager
import com.fittrackapp.fittrack_mobile.domain.model.Activity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class StatisticsViewModel @Inject constructor(
) : ViewModel() {
    private val _state = MutableStateFlow(
        StatisticsViewState(
            activities = List(30) { i ->
                val date = Date.from(
                    LocalDate.now().minusDays((29 - i).toLong())
                        .atStartOfDay(ZoneId.systemDefault())
                        .toInstant()
                )
                Activity(
                    id = i + 1,
                    startTime = date,
                    endTime = date,
                    type = "walking",
                    steps = (100..12000).random(),
                    distance = (1..20).random().toDouble(),
                    calories = (400..500).random().toDouble(),
                    duration = (30..3600).random().toLong(),
                )
            }
        )
    )
    public val state = _state.asStateFlow()

    public fun onActivitySelected(activity: Activity) {
        _state.value = _state.value.copy(currentActivity = activity)
    }
}