package com.fittrackapp.fittrack_mobile.utils

import com.fittrackapp.fittrack_mobile.data.local.entity.ActivityEntity
import com.fittrackapp.fittrack_mobile.data.local.entity.ActivityType
import com.fittrackapp.fittrack_mobile.domain.model.Activity

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

fun ActivityEntity.toString(): String {
    return "ActivityEntity(id=$id, type=$type, startTime=$startTime, endTime=$endTime, duration=$duration, distance=$distance, caloriesBurned=$caloriesBurned, steps=$steps, reps=$reps, challengeId=$challengeId, tracks=$tracks)"
}