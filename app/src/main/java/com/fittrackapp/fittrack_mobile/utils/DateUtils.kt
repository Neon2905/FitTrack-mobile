package com.fittrackapp.fittrack_mobile.utils

import java.util.Date


fun Date.format(format: String = "yyyy-MM-dd HH:mm:ss"): String {
    val formatter = java.text.SimpleDateFormat(format, java.util.Locale.getDefault())
    return formatter.format(this)
}

fun Date.getStartAndEndOfDay(): Pair<Long, Long> {
    return this.getStartOfDay() to this.getEndOfDay()
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

fun Date.getStartOfWeek(): Long {
    val calendar = java.util.Calendar.getInstance()
    calendar.time = this
    calendar.set(java.util.Calendar.DAY_OF_WEEK, calendar.firstDayOfWeek)
    calendar.set(java.util.Calendar.HOUR_OF_DAY, 0)
    calendar.set(java.util.Calendar.MINUTE, 0)
    calendar.set(java.util.Calendar.SECOND, 0)
    calendar.set(java.util.Calendar.MILLISECOND, 0)
    return calendar.timeInMillis
}

fun Date.getEndOfWeek(): Long {
    val calendar = java.util.Calendar.getInstance()
    calendar.time = this
    calendar.set(java.util.Calendar.DAY_OF_WEEK, calendar.firstDayOfWeek + 6)
    calendar.set(java.util.Calendar.HOUR_OF_DAY, 23)
    calendar.set(java.util.Calendar.MINUTE, 59)
    calendar.set(java.util.Calendar.SECOND, 59)
    calendar.set(java.util.Calendar.MILLISECOND, 999)
    return calendar.timeInMillis
}

fun Date.getStartAndEndOfWeek(): Pair<Long, Long> {
    return this.getStartOfWeek() to this.getEndOfWeek()
}

fun Date.getStartOfMonth(): Long {
    val calendar = java.util.Calendar.getInstance()
    calendar.time = this
    calendar.set(java.util.Calendar.DAY_OF_MONTH, 1)
    calendar.set(java.util.Calendar.HOUR_OF_DAY, 0)
    calendar.set(java.util.Calendar.MINUTE, 0)
    calendar.set(java.util.Calendar.SECOND, 0)
    calendar.set(java.util.Calendar.MILLISECOND, 0)
    return calendar.timeInMillis
}

fun Date.getEndOfMonth(): Long {
    val calendar = java.util.Calendar.getInstance()
    calendar.time = this
    calendar.set(
        java.util.Calendar.DAY_OF_MONTH,
        calendar.getActualMaximum(java.util.Calendar.DAY_OF_MONTH)
    )
    calendar.set(java.util.Calendar.HOUR_OF_DAY, 23)
    calendar.set(java.util.Calendar.MINUTE, 59)
    calendar.set(java.util.Calendar.SECOND, 59)
    calendar.set(java.util.Calendar.MILLISECOND, 999)
    return calendar.timeInMillis
}

fun Date.getStartAndEndOfMonth(): Pair<Long, Long> {
    return this.getStartOfMonth() to this.getEndOfMonth()
}

fun Date.getStartOfYear(): Long {
    val calendar = java.util.Calendar.getInstance()
    calendar.time = this
    calendar.set(java.util.Calendar.MONTH, java.util.Calendar.JANUARY)
    calendar.set(java.util.Calendar.DAY_OF_MONTH, 1)
    calendar.set(java.util.Calendar.HOUR_OF_DAY, 0)
    calendar.set(java.util.Calendar.MINUTE, 0)
    calendar.set(java.util.Calendar.SECOND, 0)
    calendar.set(java.util.Calendar.MILLISECOND, 0)
    return calendar.timeInMillis
}

fun Date.getEndOfYear(): Long {
    val calendar = java.util.Calendar.getInstance()
    calendar.time = this
    calendar.set(java.util.Calendar.MONTH, java.util.Calendar.DECEMBER)
    calendar.set(java.util.Calendar.DAY_OF_MONTH, 31)
    calendar.set(java.util.Calendar.HOUR_OF_DAY, 23)
    calendar.set(java.util.Calendar.MINUTE, 59)
    calendar.set(java.util.Calendar.SECOND, 59)
    calendar.set(java.util.Calendar.MILLISECOND, 999)
    return calendar.timeInMillis
}

fun Date.getStartAndEndOfYear(): Pair<Long, Long> {
    return this.getStartOfYear() to this.getEndOfYear()
}