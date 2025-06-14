package com.example.fittrack.navigation

import LoginScreen
import LoginViewModel
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fittrack.ui.screens.dashboard.DashboardScreen
import com.example.fittrack.ui.screens.setting.SettingScreen


@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            val viewModel: LoginViewModel = viewModel()

            LaunchedEffect(viewModel) {
                viewModel.navigation.collect { event ->
                    when (event) {
                        is LoginViewModel.NavigationEvent.ToDashboard -> {
                            Log.d("AppNavHost", "Navigating to settings")
                            navController.navigate("dashboard") {
                                popUpTo("login") { inclusive = true }
                            }
                        }

                        else -> {}
                    }
                }
            }

            LoginScreen(viewModel)
        }

        composable("dashboard") {
            DashboardScreen()
        }

        composable("settings") {
            SettingScreen()
        }
    }
}