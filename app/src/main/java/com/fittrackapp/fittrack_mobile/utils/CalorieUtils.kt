package com.fittrackapp.fittrack_mobile.utils

import android.annotation.SuppressLint
import android.util.Log
import com.fittrackapp.fittrack_mobile.data.local.entity.ActivityType
import kotlin.time.Duration

object CalorieUtils {

    @SuppressLint("DefaultLocale")
    fun calculateCalories(
        steps: Int = 0,
        distanceKm: Double = 0.0,
        duration: Duration,
        weightKg: Double = 70.0,
        activityType: ActivityType = ActivityType.WALKING,
        liftWeightKg: Double = 0.0,
        laps: Int = 0,
        poolLength: Double = 0.0, // in meters
        sets: Int = 0,
        reps: Int = 0
    ): Double {

        val seconds = duration.inWholeMilliseconds.toDouble() / 1000
        val hours = seconds / 3600.0
        val minutes = seconds / 60.0

        if (activityType == ActivityType.UNKNOWN) {
            Log.w("CalorieUtils", "Unknown activity type, returning 0 calories")
            return 0.0
        }

        val met = when (activityType) {
            ActivityType.WALKING,
            ActivityType.RUNNING,
            ActivityType.CYCLING -> {
                val speedKph = if (minutes > 0) distanceKm / (minutes / 60.0) else 0.0
                estimateMet(speedKph)
            }

            ActivityType.SWIMMING -> {
                val swimDistanceKm = (laps * poolLength) / 1000.0
                val swimSpeedKph = if (minutes > 0) swimDistanceKm / (minutes / 60.0) else 0.0
                // Swimming is more intense: custom METs
                when {
                    swimSpeedKph < 2.0 -> 6.0
                    swimSpeedKph < 3.0 -> 8.0
                    else -> 10.0
                }
            }

            ActivityType.WEIGHTLIFTING -> {
                val volume = sets * reps * liftWeightKg
                when {
                    volume < 1000 -> 3.0
                    volume < 3000 -> 4.0
                    else -> 6.0
                }
            }

            else -> {
                // This will never reach
                1.0
            }
        }

        val caloriesBurned = met * weightKg * hours
        return String.format("%.2f", caloriesBurned).toDouble()
    }

    /**
     * Smooth MET estimation based on speed (km/h)
     */
    private fun estimateMet(speedKph: Double): Double {
        return when {
            speedKph <= 3.0 -> 2.0
            speedKph <= 5.0 -> interpolate(speedKph, 3.0, 5.0, 2.0, 3.5)
            speedKph <= 7.0 -> interpolate(speedKph, 5.0, 7.0, 3.5, 6.0)
            speedKph <= 10.0 -> interpolate(speedKph, 7.0, 10.0, 6.0, 8.0)
            speedKph <= 15.0 -> interpolate(speedKph, 10.0, 15.0, 8.0, 10.0)
            else -> 10.0
        }
    }

    /**
     * Linear interpolation
     */
    private fun interpolate(x: Double, x0: Double, x1: Double, y0: Double, y1: Double): Double {
        return y0 + (x - x0) * (y1 - y0) / (x1 - x0)
    }
}