package com.fittrackapp.fittrack_mobile.presentation

import com.fittrackapp.fittrack_mobile.domain.model.Activity

data class StatisticsViewState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isBottomSheetOpen: Boolean = false,
    val selectedDate: String? = null,
    val selectedUserId: String? = null,
    val activities: List<Activity> = emptyList(),
    val currentActivity: Activity? = null,
)