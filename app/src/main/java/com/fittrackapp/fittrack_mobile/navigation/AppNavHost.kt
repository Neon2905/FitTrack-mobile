package com.fittrackapp.fittrack_mobile.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
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
import com.fittrackapp.fittrack_mobile.presentation.dashboard.DashboardViewModel
import com.fittrackapp.fittrack_mobile.presentation.register.RegisterLiveActivityScreen
import com.fittrackapp.fittrack_mobile.presentation.setting.SettingScreen
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject


@Composable
fun AppNavHost() {
    val securePrefsManager = SecurePrefsManager(LocalContext.current.applicationContext)

    // Initialize the app vm
    val dashboardViewModel: DashboardViewModel = hiltViewModel()
    val registerLiveActivityViewModel: RegisterLiveActivityViewModel = hiltViewModel()

    val navController = rememberNavController()
    SyncNavigator(navController)

    LaunchedEffect(Unit) {
        Navigator.setController(navController)
    }

    val context = LocalContext.current.applicationContext

    NavHost(
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
            //TODO
        }

        composable(
            route = NavRoute.ImportedScreen.route
        ) {
<<<<<<< HEAD
            ExperimentScreen()
=======
            ImportedScreen()
>>>>>>> e9395b67bc9ea21fd1b762fcf0316bbe8b74d41a
        }

        composable(
            route = NavRoute.Register.LiveActivity.route,
        ) {
            RegisterLiveActivityScreen(registerLiveActivityViewModel)
        }
        // TODO: Add more routes here
    }
}