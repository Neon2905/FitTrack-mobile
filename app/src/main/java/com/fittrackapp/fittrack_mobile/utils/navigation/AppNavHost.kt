package com.fittrackapp.fittrack_mobile.utils.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import com.fittrackapp.fittrack_mobile.presentation.auth.LoginScreen
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.fittrackapp.fittrack_mobile.presentation.dashboard.DashboardScreen
import com.fittrackapp.fittrack_mobile.presentation.setting.SettingScreen

@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    LaunchedEffect(Unit) {
        Navigator.setController(navController)
    }

    NavHost(
        navController = navController,
        startDestination = NavRoute.Login.route,
        enterTransition = { slideInHorizontally { it } + fadeIn() },
        exitTransition = { slideOutHorizontally { -it } + fadeOut() },
        popEnterTransition = { slideInHorizontally { -it } + fadeIn() },
        popExitTransition = { slideOutHorizontally { it } + fadeOut() }
    ) {
        composable(NavRoute.Login.route) { LoginScreen() }
        composable(NavRoute.Dashboard.route) { DashboardScreen() }
        composable(NavRoute.Settings.route) { SettingScreen() }
        // TODO: Add more routes here
    }
}