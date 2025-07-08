package com.fittrackapp.fittrack_mobile.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fittrackapp.fittrack_mobile.data.local.dao.ActivityDao
import com.fittrackapp.fittrack_mobile.data.local.entity.ActivityEntity
import com.fittrackapp.fittrack_mobile.data.local.entity.ActivityType
import com.fittrackapp.fittrack_mobile.presentation.auth.AuthViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject
import kotlinx.coroutines.flow.stateIn

@HiltViewModel
class ExperimentViewModel @Inject constructor(
    private val activityDao: ActivityDao
) : ViewModel() {
    init {
        viewModelScope.launch {
            activityDao.getAll().collect { activities ->
                _state.update { it.copy(activities = activities) }
            }
        }
    }

    fun deleteAllActivities() {
        viewModelScope.launch {
            activityDao.deleteAll()
        }
    }

    private val _state = MutableStateFlow(State())
    val state = _state.asStateFlow()
    fun addActivity() {
        viewModelScope.launch {
            activityDao.add(
                ActivityEntity(
                    id = 0,
                    type = ActivityType.RUNNING,
                    startTime = Date(),
                    endTime = Date(),
                    duration = (5000..15000).random().toDouble(), // 1 hour in seconds
                    distance = (5000..15000).random().toDouble(), // 10 kilometers in meters
                    caloriesBurned = (5000..15000).random().toDouble(), // Example value in kcal
                    steps = (5000..15000).random(), // Example value for steps
                )
            )
        }
    }
}

data class State(
    val activities: List<ActivityEntity> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)