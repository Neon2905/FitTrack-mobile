package com.fittrackapp.fittrack_mobile.ui.dashboard

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.fittrackapp.fittrack_mobile.data.model.Activity
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date

class DashboardViewModel : ViewModel() {
    val activities = mutableStateOf(List(30) { i ->
        val date = Date.from(
            LocalDate.now().minusDays((29 - i).toLong()).atStartOfDay(ZoneId.systemDefault())
                .toInstant()
        )
        Activity(id = i + 1, date = date, calories = (400..900).random())
    })

    var currentActivity = mutableStateOf<Activity>(activities.value[0])

    fun onActivitySelected(activity: Activity) {
        currentActivity.value = activity
    }
}