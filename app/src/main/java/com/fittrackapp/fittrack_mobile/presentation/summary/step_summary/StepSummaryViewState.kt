package com.fittrackapp.fittrack_mobile.presentation.summary.step_summary

data class StepSummaryViewState(
    val steps: Int = 0,
    val distance: String = "0.0",
    val calories: String = "0.0",
    val activeTime: String = "00:00:00",
    val errorMessage: String? = null
){

}