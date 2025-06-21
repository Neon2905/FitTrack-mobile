package com.fittrackapp.fittrack_mobile.domain.model

import java.util.Date

data class Activity(
    val id: Int,
    val date: Date,
    val calories: Int,
)
