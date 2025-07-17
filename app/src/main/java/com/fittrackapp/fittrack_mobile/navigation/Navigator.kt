package com.fittrackapp.fittrack_mobile.navigation

import android.annotation.SuppressLint
import androidx.navigation.NavHostController
import com.fittrackapp.fittrack_mobile.data.local.SecurePrefsManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

object Navigator {
    lateinit var securePrefsManager: SecurePrefsManager

    fun init(securePrefsManager: SecurePrefsManager) {
        this.securePrefsManager = securePrefsManager
    }

    @SuppressLint("StaticFieldLeak")
    private var navController: NavHostController? = null

    private val _currentRoute = MutableStateFlow<String>("")
    val currentRoute: StateFlow<String> = _currentRoute.asStateFlow()

    private val _previousRoute = MutableStateFlow<String>("")
    val previousRoute: StateFlow<String> = _previousRoute.asStateFlow()

    fun setController(controller: NavHostController) {
        navController = controller
    }

    fun setCurrentRoute(route: String) {
        _previousRoute.value = _currentRoute.value
        _currentRoute.value = route
    }

    fun navigate(route: String) {

        if (_currentRoute.value == route)
            return

        // Prevent navigation to Auth route if user is already authenticated
        if (route == NavRoute.Auth.route && securePrefsManager.getAuthUser() != null)
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