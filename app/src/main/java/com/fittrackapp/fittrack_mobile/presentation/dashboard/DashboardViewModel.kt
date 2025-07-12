package com.fittrackapp.fittrack_mobile.presentation.dashboard

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fittrackapp.fittrack_mobile.data.local.SecurePrefsManager
import com.fittrackapp.fittrack_mobile.data.local.dao.ActivityDao
import com.fittrackapp.fittrack_mobile.data.local.entity.ActivityEntity
import com.fittrackapp.fittrack_mobile.domain.model.Activity
import com.fittrackapp.fittrack_mobile.domain.repository.SecurePrefsRepository
import com.fittrackapp.fittrack_mobile.presentation.auth.AuthViewState
import com.fittrackapp.fittrack_mobile.utils.getEndOfDay
import com.fittrackapp.fittrack_mobile.utils.getStartAndEndOfDay
import com.fittrackapp.fittrack_mobile.utils.getStartOfDay
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
class DashboardViewModel @Inject constructor(
    private val securePrefsManager: SecurePrefsManager,
    private val activityDao: ActivityDao
) : ViewModel() {

    private val _state = MutableStateFlow(
        DashboardViewState()
    )
    val state = _state.asStateFlow()

    init {
        // Fetch today's activities
        viewModelScope.launch {
            val (start, end) = Date().getStartAndEndOfDay()
            activityDao.getAllByTime(start, end).collect { activities ->
                _state.update { it.copy(activities = activities) }
            }
        }

        // Fetch last week's activities
        viewModelScope.launch {
            val start = Date().apply { time -= 7 * 24 * 60 * 60 * 1000 }.getStartOfDay()
            val end = Date().getEndOfDay()
            activityDao.getAllByTime(start, end).collect { lastWeekActivities ->
                _state.update { it.copy(lastWeekActivities = lastWeekActivities) }
            }
        }
    }

    fun getUsername(): String? {
        return securePrefsManager.getAuthUser()?.username ?: "Guest"
    }
}