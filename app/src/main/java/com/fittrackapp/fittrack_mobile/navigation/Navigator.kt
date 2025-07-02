package com.fittrackapp.fittrack_mobile.navigation

import android.annotation.SuppressLint
import androidx.navigation.NavHostController
import com.fittrackapp.fittrack_mobile.domain.repository.SecurePrefsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object Navigator {
    lateinit var securePrefsRepository: SecurePrefsRepository

    fun init(securePrefsRepository: SecurePrefsRepository) {
        this.securePrefsRepository = securePrefsRepository
    }

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

        // Prevent navigation to Auth route if user is already authenticated
        if (route == NavRoute.Auth.route && securePrefsRepository.getAuthUser() != null)
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