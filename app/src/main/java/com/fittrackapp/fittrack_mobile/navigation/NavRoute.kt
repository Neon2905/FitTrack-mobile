package com.fittrackapp.fittrack_mobile.navigation

sealed class NavRoute(val route: String) {
    data object Auth : NavRoute("auth")
    data object Dashboard : NavRoute("dashboard")
    data object Settings : NavRoute("settings")
    data object Statistics : NavRoute("statistics")
    data object Summary : NavRoute("summary")

    data object Register {
        data object LiveActivity : NavRoute("register/live_activity")
        data object LogActivity : NavRoute("register/log_activity")
    }
}