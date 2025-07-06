package com.fittrackapp.fittrack_mobile.utils

import android.annotation.SuppressLint
import android.util.Log
import kotlin.time.Duration

object CalorieUtils {

    @SuppressLint("DefaultLocale")
    fun calculateCalories(
        steps: Int,
        distanceKm: Double,
        duration: Duration,
        weightKg: Double = 70.0
    ): Double {

        val seconds: Double = duration.inWholeMilliseconds.toDouble() / 1000 // Convert to seconds
        val hours: Double = seconds / 3600.0
        val minutes: Double = seconds / 60.0

        val speedKph: Double = distanceKm / (minutes / 60.0)

        val met = estimateMet(speedKph)

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