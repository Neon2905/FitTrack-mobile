package com.fittrackapp.fittrack_mobile.utils

import com.fittrackapp.fittrack_mobile.data.local.entity.ActivityType

fun String.asActivityType(): ActivityType {
    return when (this.lowercase()) {
        "running" -> ActivityType.RUNNING
        "walking" -> ActivityType.WALKING
        "cycling" -> ActivityType.CYCLING
        "swimming" -> ActivityType.SWIMMING
        "weightlifting" -> ActivityType.WEIGHTLIFTING
        "unknown" -> ActivityType.UNKNOWN
        else -> ActivityType.UNKNOWN // Default case for unrecognized types
    }
}

fun ActivityType.asString(): String {
    return when (this) {
        ActivityType.RUNNING -> "Running"
        ActivityType.WALKING -> "Walking"
        ActivityType.CYCLING -> "Cycling"
        ActivityType.SWIMMING -> "Swimming"
        ActivityType.WEIGHTLIFTING -> "Weight Lifting"
        ActivityType.UNKNOWN -> "Unknown"
    }
}