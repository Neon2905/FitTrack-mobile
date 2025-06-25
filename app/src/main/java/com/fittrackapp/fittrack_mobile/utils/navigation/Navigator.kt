package com.fittrackapp.fittrack_mobile.utils.navigation

import android.annotation.SuppressLint
import android.util.Log
import androidx.navigation.NavHostController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object Navigator {
    @SuppressLint("StaticFieldLeak")
    private var navController: NavHostController? = null

    private val _currentRoute = MutableStateFlow<String>("")
    val currentRoute: StateFlow<String> = _currentRoute.asStateFlow()

    fun setController(controller: NavHostController) {
        navController = controller
    }

    internal fun setCurrentRoute(route: String) {
        _currentRoute.value = route
    }

    fun navigate(route: String) {

        if (_currentRoute.value == route)
            return

        navController?.navigate(route)
    }

    fun navigateAndClear(route: String) {
        if (_currentRoute.value == route)
            return

        navController?.navigate(route) {
            popUpTo(0)
        }
    }

    fun goBack() {
        navController?.popBackStack()
    }
}