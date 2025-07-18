package com.fittrackapp.fittrack_mobile.utils

import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration


/**
 * Formats a duration into "H:MM:SS" format.
 * @param duration the duration to format
 * @return formatted string like "0:17:23"
 */
fun Duration.formatString(format: String = "%d:%02d:%02d"): String {
    val totalSeconds = this.inWholeSeconds
    val hours = totalSeconds / 3600
    val minutes = (totalSeconds % 3600) / 60
    val seconds = totalSeconds % 60
    return String.format(format, hours, minutes, seconds)
}

/**
 * Overload to accept seconds as Long
 */
fun Long.formatDurationString(seconds: Long): String {
    return this.toDuration(DurationUnit.SECONDS).formatString()
}