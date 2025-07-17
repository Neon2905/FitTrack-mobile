package com.fittrackapp.fittrack_mobile.navigation

import android.util.Log
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
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
import com.fittrackapp.fittrack_mobile.presentation.dashboard.DashboardScreen
import com.fittrackapp.fittrack_mobile.presentation.register.RegisterLiveActivityViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import com.fittrackapp.fittrack_mobile.data.local.SecurePrefsManager
import com.fittrackapp.fittrack_mobile.presentation.AuthScreen
import com.fittrackapp.fittrack_mobile.presentation.StatisticsScreen
import com.fittrackapp.fittrack_mobile.presentation.dashboard.DashboardViewModel
import com.fittrackapp.fittrack_mobile.presentation.navItems
import com.fittrackapp.fittrack_mobile.presentation.register.RegisterLiveActivityScreen
import com.fittrackapp.fittrack_mobile.presentation.register.log_activity.LogActivityScreen
import com.fittrackapp.fittrack_mobile.presentation.setting.SettingScreen
import com.fittrackapp.fittrack_mobile.presentation.summary.SummaryScreen


@Composable
fun AppNavHost(modifier: Modifier = Modifier) {
    val securePrefsManager = SecurePrefsManager(LocalContext.current.applicationContext)

    // Initialize the app vm
    val dashboardViewModel: DashboardViewModel = hiltViewModel()
    val registerLiveActivityViewModel: RegisterLiveActivityViewModel = hiltViewModel()

    val navController = rememberNavController()
    // Sync custom Navigator with the navController
    SyncNavigator(navController)

    LaunchedEffect(Unit) {
        Navigator.setController(navController)
    }

    val defaultScreen: NavRoute =
        if (securePrefsManager.getAuthUser() != null) {
            NavRoute.Dashboard
        } else {
            NavRoute.Auth
        }

    // <editor-fold desc="Transition Animations">
    val animationDelayMillis = 700

    val defaultEnterTransition: EnterTransition = fadeIn(
        animationSpec = tween(animationDelayMillis)
    )

    val defaultExitTransition: ExitTransition = fadeOut(
        animationSpec = tween(animationDelayMillis)
    )

    fun slideInTransition(direction: AnimatedContentTransitionScope.SlideDirection): EnterTransition {
        return when (direction) {
            AnimatedContentTransitionScope.SlideDirection.Left -> slideInHorizontally(
                initialOffsetX = { it }, animationSpec = tween(animationDelayMillis)
            )

            AnimatedContentTransitionScope.SlideDirection.Right -> slideInHorizontally(
                initialOffsetX = { -it }, animationSpec = tween(animationDelayMillis)
            )

            AnimatedContentTransitionScope.SlideDirection.Up -> slideInVertically(
                initialOffsetY = { it }, animationSpec = tween(animationDelayMillis)
            )

            AnimatedContentTransitionScope.SlideDirection.Down -> slideInVertically(
                initialOffsetY = { -it }, animationSpec = tween(animationDelayMillis)
            )

            else -> defaultEnterTransition
        } + fadeIn(
            animationSpec = tween((animationDelayMillis * 1.5).toInt())
        )
    }

    fun slideOutTransition(direction: AnimatedContentTransitionScope.SlideDirection): ExitTransition {
        return when (direction) {
            AnimatedContentTransitionScope.SlideDirection.Left -> slideOutHorizontally(
                targetOffsetX = { -it }, animationSpec = tween(animationDelayMillis)
            )

            AnimatedContentTransitionScope.SlideDirection.Right -> slideOutHorizontally(
                targetOffsetX = { it }, animationSpec = tween(animationDelayMillis)
            )

            AnimatedContentTransitionScope.SlideDirection.Up -> slideOutVertically(
                targetOffsetY = { -it }, animationSpec = tween(animationDelayMillis)
            )

            AnimatedContentTransitionScope.SlideDirection.Down -> slideOutVertically(
                targetOffsetY = { it }, animationSpec = tween(animationDelayMillis)
            )

            else -> defaultExitTransition
        } + fadeOut()
    }

    fun isRouteFromNavBarItems(): Boolean =
        Navigator.previousRoute.value in navItems.map { it.route }

    fun isRouteToNavBarItems(): Boolean =
        Navigator.currentRoute.value in navItems.map { it.route }

    fun decideNavBarItemsEnterTransition(route: String): EnterTransition {
        return slideInTransition(
            direction =
                if (navItems.indexOfFirst { it.route == Navigator.previousRoute.value } >
                    navItems.indexOfFirst { it.route == route }) {
                    // Comes from right
                    AnimatedContentTransitionScope.SlideDirection.Right
                } else
                    AnimatedContentTransitionScope.SlideDirection.Left
        )
    }

    fun decideNavBarItemsExitTransition(route: String): ExitTransition {
        return slideOutTransition(
            direction =
                if (navItems.indexOfFirst { it.route == Navigator.previousRoute.value } >
                    navItems.indexOfFirst { it.route == route }) {
                    // Comes from right
                    AnimatedContentTransitionScope.SlideDirection.Right
                } else
                    AnimatedContentTransitionScope.SlideDirection.Left
        )
    }
    // </editor-fold>

    NavHost(
        modifier = modifier.padding(
            WindowInsets.systemBars.only(
                WindowInsetsSides.Horizontal + WindowInsetsSides.Top
            ).asPaddingValues()
        ),
        navController = navController,
        startDestination = defaultScreen.route,
        enterTransition = {
            if (isRouteFromNavBarItems() && isRouteToNavBarItems()) {
                decideNavBarItemsEnterTransition(Navigator.currentRoute.value)
            } else
                defaultEnterTransition
        },
        exitTransition = {
            if (isRouteFromNavBarItems() && isRouteToNavBarItems()) {
                decideNavBarItemsExitTransition(Navigator.currentRoute.value)
            } else if (isRouteToNavBarItems()) {
                slideOutTransition(
                    AnimatedContentTransitionScope.SlideDirection.Right
                )
            } else
                defaultExitTransition
        },
        popEnterTransition = { defaultEnterTransition },
        popExitTransition = { defaultExitTransition }
    ) {

        composable(
            route = NavRoute.Auth.route,
            enterTransition = {
                slideInTransition(
                    AnimatedContentTransitionScope.SlideDirection.Up
                )
            }, exitTransition = {
                slideOutTransition(
                    AnimatedContentTransitionScope.SlideDirection.Down
                )
            }) {
            AuthScreen()
        }

        composable(
            route = NavRoute.Dashboard.route
        ) {
            DashboardScreen(dashboardViewModel)
        }

        composable(
            route = NavRoute.Statistics.route
        ) {
            StatisticsScreen()
        }

        composable(
            route = NavRoute.Summary.route
        ) {
            SummaryScreen()
        }

        composable(
            route = NavRoute.Settings.route
        ) {
            SettingScreen()
        }

        composable(
            route = NavRoute.Register.LiveActivity.route,
            enterTransition = {
                slideInTransition(
                    AnimatedContentTransitionScope.SlideDirection.Up
                )
            }
        ) {
            RegisterLiveActivityScreen(registerLiveActivityViewModel)
        }

        composable(
            route = NavRoute.Register.LogActivity.route,
            enterTransition = {
                slideInTransition(
                    AnimatedContentTransitionScope.SlideDirection.Up
                )
            }
        ) {
            LogActivityScreen()
        }
    }
}