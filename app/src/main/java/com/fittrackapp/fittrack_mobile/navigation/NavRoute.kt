package com.fittrackapp.fittrack_mobile.navigation

sealed class NavRoute(val route: String) {
    data object Auth : NavRoute("auth")
    data object Dashboard : NavRoute("dashboard")
    data object Settings : NavRoute("settings")
}