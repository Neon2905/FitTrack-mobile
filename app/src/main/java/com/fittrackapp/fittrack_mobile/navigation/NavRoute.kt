package com.fittrackapp.fittrack_mobile.navigation

sealed class NavRoute(val route: String) {
    data object Login : NavRoute("login")
    data object Dashboard : NavRoute("dashboard")
    data object Settings : NavRoute("settings")
}