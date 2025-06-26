package com.fittrackapp.fittrack_mobile.domain.model

import java.util.Date

data class Activity(
    val id: Int,
    val type: String,
    val startTime: Date,
    val endTime: Date,
    val steps: Int,
    val distance: Float,
    val calories: Float,
)
