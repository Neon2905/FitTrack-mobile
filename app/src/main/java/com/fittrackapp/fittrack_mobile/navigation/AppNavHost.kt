package com.fittrackapp.fittrack_mobile.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.fittrackapp.fittrack_mobile.presentation.auth.AuthScreen
import com.fittrackapp.fittrack_mobile.presentation.dashboard.DashboardScreen
import com.fittrackapp.fittrack_mobile.presentation.register.RegisterWalkingScreen
import com.fittrackapp.fittrack_mobile.presentation.setting.SettingScreen

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    SyncNavigator(navController)

    LaunchedEffect(Unit) {
        Navigator.setController(navController)
    }

    NavHost(
        navController = navController,
        startDestination = NavRoute.Dashboard.route,
        enterTransition = { slideInHorizontally { it } + fadeIn() },
        exitTransition = { slideOutHorizontally { -it } + fadeOut() },
        popEnterTransition = { slideInHorizontally { -it } + fadeIn() },
        popExitTransition = { slideOutHorizontally { it } + fadeOut() }
    ) {
        composable(
            route = NavRoute.Auth.route,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Up,
                    tween(700)
                )
            }
        ) {
            AuthScreen()
        }
        composable(
            route = NavRoute.Dashboard.route,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    tween(700)
                )
            }
        )
        {
            DashboardScreen()
        }
        composable(
            route = NavRoute.Settings.route,
        ) {
            SettingScreen()
        }
        composable(
            route = NavRoute.Dashboard.route,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    tween(700)
                )
            }
        )
        {
            RegisterWalkingScreen()
        }
        // TODO: Add more routes here
    }
}