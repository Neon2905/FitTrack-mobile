package com.fittrackapp.fittrack_mobile.presentation.dashboard

import com.fittrackapp.fittrack_mobile.domain.model.Activity

data class DashboardViewState(
    val activities: List<Activity>,
    var currentActivity: Activity? = activities.getOrNull(0)
)