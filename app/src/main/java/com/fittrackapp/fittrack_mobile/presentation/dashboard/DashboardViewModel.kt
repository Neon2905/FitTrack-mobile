package com.fittrackapp.fittrack_mobile.presentation.dashboard

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.fittrackapp.fittrack_mobile.domain.model.Activity
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date

class DashboardViewModel : ViewModel() {
    val activities = mutableStateOf(List(30) { i ->
        val date = Date.from(
            LocalDate.now().minusDays((29 - i).toLong()).atStartOfDay(ZoneId.systemDefault())
                .toInstant()
        )
        Activity(id = i + 1, startTime = date, endTime = date, type = "walking", steps = (100..12000).random(), distance = (1..20).random().toFloat(),  calories = (400..900).random().toFloat())
    })

    var currentActivity = mutableStateOf<Activity>(activities.value[0])

    fun onActivitySelected(activity: Activity) {
        currentActivity.value = activity
    }
}