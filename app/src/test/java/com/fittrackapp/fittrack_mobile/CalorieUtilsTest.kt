package com.fittrackapp.fittrack_mobile

import com.fittrackapp.fittrack_mobile.utils.CalorieUtils
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.time.DurationUnit
import kotlin.time.toDuration

class CalorieUtilsTest {

    @Test
    fun testWalkingCalories() {
        val calories = CalorieUtils.calculateCalories(
            steps = 3000,
            distanceKm = 2.5,
            duration = 30.toDuration(DurationUnit.SECONDS), // 30 minutes
            weightKg = 70.0
        )
        assertEquals(245.5, calories, 1.0)
    }

    @Test
    fun testRunningCalories() {
        val calories = CalorieUtils.calculateCalories(
            steps = 5000,
            distanceKm = 4.0,
            duration = 25.toDuration(DurationUnit.SECONDS),
            weightKg = 70.0
        )
        assertEquals(204.0, calories, 1.0)
    }

    @Test
    fun testZeroDuration() {
        val calories = CalorieUtils.calculateCalories(
            steps = 1000,
            distanceKm = 1.0,
            duration = 0.toDuration(DurationUnit.SECONDS),
            weightKg = 70.0
        )
        assertEquals(0.0, calories, 0.01)
    }
}