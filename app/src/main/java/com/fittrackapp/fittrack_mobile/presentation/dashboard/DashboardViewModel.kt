package com.fittrackapp.fittrack_mobile.presentation.dashboard

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.fittrackapp.fittrack_mobile.data.local.SecurePrefsManager
import com.fittrackapp.fittrack_mobile.data.local.dao.ActivityDao
import com.fittrackapp.fittrack_mobile.domain.model.Activity
import com.fittrackapp.fittrack_mobile.domain.repository.SecurePrefsRepository
import com.fittrackapp.fittrack_mobile.presentation.auth.AuthViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val securePrefsManager: SecurePrefsManager
)  : ViewModel() {

    private val _state = MutableStateFlow(
        DashboardViewState(
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
                    distance = (1..20).random().toFloat(),
                    calories = (400..500).random().toFloat()
                )
            },
            authUser = securePrefsManager.getAuthUser()
        )
    )
    val state = _state.asStateFlow()

    fun getUsername(): String? {
        return securePrefsManager.getAuthUser()?.username ?: "Guest"
    }

    fun onActivitySelected(activity: Activity) {
        _state.value = _state.value.copy(currentActivity = activity)
        Log.i("DashboardViewModel", "Selected activity: ${_state.value.currentActivity}")
    }
}