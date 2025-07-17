package com.fittrackapp.fittrack_mobile.utils

import com.fittrackapp.fittrack_mobile.data.local.entity.ActivityEntity
import com.fittrackapp.fittrack_mobile.data.local.entity.ActivityType
import com.fittrackapp.fittrack_mobile.domain.model.Activity
import kotlin.time.Duration

fun Activity.asEntity(isUploaded: Boolean = false): ActivityEntity {
    return ActivityEntity(
        id = this.id,
        type = when (this.type) {
            "WALKING" -> ActivityType.WALKING
            "RUNNING" -> ActivityType.RUNNING
            "CYCLING" -> ActivityType.CYCLING
            "WEIGHTLIFTING" -> ActivityType.WEIGHTLIFTING
            else -> ActivityType.UNKNOWN
        },
        startTime = this.startTime,
        endTime = this.endTime,
        duration = this.duration,
        distance = this.distance,
        caloriesBurned = this.calories,
        steps = this.steps,
        reps = this.reps,
        challengeId = this.challengeId,
        tracks = this.tracks,
        isUploaded = isUploaded
    )
}

fun ActivityEntity.asActivity(): Activity {
    return Activity(
        id = this.id,
        type = this.type.name,
        startTime = this.startTime,
        endTime = this.endTime,
        duration = this.duration,
        distance = this.distance,
        calories = this.caloriesBurned,
        steps = this.steps,
        reps = this.reps,
        challengeId = this.challengeId,
        tracks = this.tracks
    )
}

fun predictActivityType(distance: Double, duration: Duration): ActivityType {
    val speed =
        if (duration.inWholeMilliseconds > 0)
            distance / (duration.inWholeMilliseconds / 1000.0 / 3600.0)
        else
            0.0 // km/h
    return when {
        speed < 5 -> ActivityType.WALKING
        speed < 20 -> ActivityType.RUNNING
        speed < 30 -> ActivityType.CYCLING
        else -> ActivityType.UNKNOWN // Default case, can be adjusted based on actual data
    }
}