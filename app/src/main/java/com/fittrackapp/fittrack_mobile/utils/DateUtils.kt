package com.fittrackapp.fittrack_mobile.utils

import java.util.Date

fun Date.getStartAndEndOfDay(): Pair<Long, Long> {
    val calendar = java.util.Calendar.getInstance()
    calendar.time = this
    calendar.set(java.util.Calendar.HOUR_OF_DAY, 0)
    calendar.set(java.util.Calendar.MINUTE, 0)
    calendar.set(java.util.Calendar.SECOND, 0)
    calendar.set(java.util.Calendar.MILLISECOND, 0)
    val startOfDay = calendar.timeInMillis
    calendar.add(java.util.Calendar.DAY_OF_MONTH, 1)
    val endOfDay = calendar.timeInMillis
    return startOfDay to endOfDay
}

fun Date.getStartOfDay(): Long {
    val calendar = java.util.Calendar.getInstance()
    calendar.time = this
    calendar.set(java.util.Calendar.HOUR_OF_DAY, 0)
    calendar.set(java.util.Calendar.MINUTE, 0)
    calendar.set(java.util.Calendar.SECOND, 0)
    calendar.set(java.util.Calendar.MILLISECOND, 0)
    return calendar.timeInMillis
}

fun Date.getEndOfDay(): Long {
    val calendar = java.util.Calendar.getInstance()
    calendar.time = this
    calendar.set(java.util.Calendar.HOUR_OF_DAY, 23)
    calendar.set(java.util.Calendar.MINUTE, 59)
    calendar.set(java.util.Calendar.SECOND, 59)
    calendar.set(java.util.Calendar.MILLISECOND, 999)
    return calendar.timeInMillis
}