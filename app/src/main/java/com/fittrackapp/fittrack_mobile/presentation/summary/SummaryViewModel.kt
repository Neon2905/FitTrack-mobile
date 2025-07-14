package com.fittrackapp.fittrack_mobile.presentation

import android.util.Log
import android.view.Menu
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fittrackapp.fittrack_mobile.data.local.dao.ActivityDao
import com.fittrackapp.fittrack_mobile.navigation.Navigator.init
import com.fittrackapp.fittrack_mobile.presentation.summary.step_summary.StepSummaryViewState
import com.fittrackapp.fittrack_mobile.utils.getStartAndEndOfPeriod
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.hours
import kotlin.time.DurationUnit

@HiltViewModel
class SummaryViewModel @Inject constructor(
    private val activityDao: ActivityDao
) : ViewModel() {
    private val _state = MutableStateFlow(
        SummaryViewState(
            selectedPeriod = SelectedPeriod.THIS_MONTH
        )
    )
    val state = _state.asStateFlow()


    init {
        fetchActivities()
    }

    fun fetchActivities() {
        viewModelScope.launch {
            activityDao.getSummaryByTime(
                _state.value.selectedStartTime,
                _state.value.selectedEndTime
            )
                .collect { activities ->
                    val (startIndex, endIndex) = when (_state.value.selectedPeriod) {
                        SelectedPeriod.TODAY -> 0 to 23
                        SelectedPeriod.THIS_WEEK -> 0 to 6
                        SelectedPeriod.THIS_MONTH -> 0 to 30
                        SelectedPeriod.THIS_YEAR -> 0 to 11
                        else -> Pair(0, 0)
                    }
                    val filteredActivities = (startIndex..endIndex).map { index ->
                        activities.filter {
                            val cal =
                                java.util.Calendar.getInstance().apply { time = it.startTime }
                            cal.get(java.util.Calendar.HOUR_OF_DAY) == index
                        }
                    }
                    _state.update {
                        it.copy(
                            filteredActivities = filteredActivities
                        )
                    }
                }
        }
    }

    fun onSelectedPeriodChanged(
        option: MenuItem
    ) {
        Log.i("SummaryViewModel", "Selected period changed: ${option.value}")
        when (option.value) {
            "TODAY" -> setSelectedPeriod(SelectedPeriod.TODAY)
            "THIS_WEEK" -> setSelectedPeriod(SelectedPeriod.THIS_WEEK)
            "THIS_MONTH" -> setSelectedPeriod(SelectedPeriod.THIS_MONTH)
            "THIS_YEAR" -> setSelectedPeriod(SelectedPeriod.THIS_YEAR)
            else -> Log.e("SummaryViewModel", "Unsupported period selected: ${option}")
        }
    }

    private fun setSelectedPeriod(selectedPeriod: SelectedPeriod) {
        _state.update { it.copy(selectedPeriod = selectedPeriod) }
        // TODO: Fix this stupid manual update [Find the correct way to trigger the flow]
        fetchActivities()
    }

}