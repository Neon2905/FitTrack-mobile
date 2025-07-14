package com.fittrackapp.fittrack_mobile.presentation.summary

import androidx.lifecycle.ViewModel
import com.fittrackapp.fittrack_mobile.data.local.dao.ActivityDao
import com.fittrackapp.fittrack_mobile.presentation.summary.step_summary.StepSummaryViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class StepSummaryViewModel @Inject constructor(
    private val activityDao: ActivityDao
) : ViewModel() {
    private val _state = MutableStateFlow(StepSummaryViewState())
    val state = _state.asStateFlow()


}