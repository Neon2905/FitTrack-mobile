package com.fittrackapp.fittrack_mobile.data.local.prefs

import kotlinx.coroutines.flow.Flow

object PrefKeys {

    private lateinit var prefs: SharedPrefsManager

    fun init(prefsManager: SharedPrefsManager) {
        prefs = prefsManager
    }

    var dailyCalorieGoal: Double
        get() = prefs.getDouble("daily_calories", 200.0)
        set(value) = prefs.putDouble("daily_calories", value)

    val dailyCalorieGoalFlow: Flow<Double>
        get() = prefs.doubleFlow("daily_calories", 200.0)
}
