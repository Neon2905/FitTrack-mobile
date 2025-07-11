package com.fittrackapp.fittrack_mobile.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.fittrackapp.fittrack_mobile.presentation.auth.AuthScreen
import com.fittrackapp.fittrack_mobile.presentation.dashboard.DashboardScreen
import com.fittrackapp.fittrack_mobile.presentation.register.RegisterLiveActivityViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import com.fittrackapp.fittrack_mobile.data.local.SecurePrefsManager
import com.fittrackapp.fittrack_mobile.presentation.ExperimentScreen
import com.fittrackapp.fittrack_mobile.presentation.StatisticsScreen
import com.fittrackapp.fittrack_mobile.presentation.dashboard.DashboardViewModel
import com.fittrackapp.fittrack_mobile.presentation.register.RegisterLiveActivityScreen
import com.fittrackapp.fittrack_mobile.presentation.setting.SettingScreen
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject


@Composable
fun AppNavHost(modifier: Modifier = Modifier) {
    val securePrefsManager = SecurePrefsManager(LocalContext.current.applicationContext)

    // Initialize the app vm
    val dashboardViewModel: DashboardViewModel = hiltViewModel()
    val registerLiveActivityViewModel: RegisterLiveActivityViewModel = hiltViewModel()

    val navController = rememberNavController()
    SyncNavigator(navController)

    LaunchedEffect(Unit) {
        Navigator.setController(navController)
    }

    NavHost(
        modifier = modifier
            .padding(
                WindowInsets.systemBars.only(
                    WindowInsetsSides.Horizontal + WindowInsetsSides.Top
                ).asPaddingValues()
            ),
        navController = navController,
        startDestination =
            if (securePrefsManager.getAuthUser() == null)
                NavRoute.Auth.route
            else
                NavRoute.Dashboard.route,
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
            DashboardScreen(dashboardViewModel)
        }

        composable(
            route = NavRoute.Settings.route,
        ) {
            SettingScreen()
        }

        composable(
            route = NavRoute.Statistics.route,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    tween(700)
                )
            }
        )
        {
            StatisticsScreen()
        }

        composable(
            route = NavRoute.ImportedScreen.route
        ) {
            ExperimentScreen()
        }

        composable(
            route = NavRoute.Register.LiveActivity.route,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Up,
                    tween(700)
                )
            }
        ) {
            RegisterLiveActivityScreen(registerLiveActivityViewModel)
        }
        // TODO: Add more routes here
    }
}