package com.fittrackapp.fittrack_mobile.utils

import android.util.Log
import com.fittrackapp.fittrack_mobile.presentation.SelectedPeriod
import java.util.Date

fun SelectedPeriod.getStartAndEndOfPeriod(): Pair<Long, Long> {
    return when (this) {
        SelectedPeriod.TODAY -> {
            Date().getStartAndEndOfDay()
        }

        SelectedPeriod.THIS_WEEK -> {
            Date().getStartAndEndOfWeek()
        }

        SelectedPeriod.THIS_MONTH -> {
            Date().getStartAndEndOfMonth()
        }

        SelectedPeriod.THIS_YEAR -> {
            Date().getStartAndEndOfYear()
        }

        else -> {
            Log.e("SelectedPeriod", "Unsupported period selected: $this")
            Pair(0L, 0L) // Represents all time or invalid period
        }
    }
}

fun SelectedPeriod.getStartOfPeriod(): Long {
    return when (this) {
        SelectedPeriod.TODAY -> Date().getStartOfDay()
        SelectedPeriod.THIS_WEEK -> Date().getStartOfWeek()
        SelectedPeriod.THIS_MONTH -> Date().getStartOfMonth()
        SelectedPeriod.THIS_YEAR -> Date().getStartOfYear()
        else -> 0L // Represents all time or invalid period
    }
}

fun SelectedPeriod.getEndOfPeriod(): Long {
    return when (this) {
        SelectedPeriod.TODAY -> Date().getEndOfDay()
        SelectedPeriod.THIS_WEEK -> Date().getEndOfWeek()
        SelectedPeriod.THIS_MONTH -> Date().getEndOfMonth()
        SelectedPeriod.THIS_YEAR -> Date().getEndOfYear()
        else -> 0L// Represents all time or invalid period
    }
}

fun SelectedPeriod.toValueString(): String {
    return when (this) {
        SelectedPeriod.TODAY -> "TODAY"
        SelectedPeriod.THIS_WEEK -> "THIS_WEEK"
        SelectedPeriod.THIS_MONTH -> "THIS_MONTH"
        SelectedPeriod.THIS_YEAR -> "THIS_YEAR"
        SelectedPeriod.ALL_TIME -> "ALL_TIME"
    }
}

