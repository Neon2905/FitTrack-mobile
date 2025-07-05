package com.fittrackapp.fittrack_mobile.navigation

import android.annotation.SuppressLint
import androidx.navigation.NavHostController
<<<<<<< HEAD
import com.fittrackapp.fittrack_mobile.data.local.SecurePrefsManager
=======
import com.fittrackapp.fittrack_mobile.domain.repository.SecurePrefsRepository
>>>>>>> e9395b67bc9ea21fd1b762fcf0316bbe8b74d41a
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

object Navigator {
<<<<<<< HEAD
    lateinit var securePrefsManager: SecurePrefsManager

    fun init(securePrefsManager: SecurePrefsManager) {
        this.securePrefsManager = securePrefsManager
=======
    lateinit var securePrefsRepository: SecurePrefsRepository

    fun init(securePrefsRepository: SecurePrefsRepository) {
        this.securePrefsRepository = securePrefsRepository
>>>>>>> e9395b67bc9ea21fd1b762fcf0316bbe8b74d41a
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
<<<<<<< HEAD
        if (route == NavRoute.Auth.route && securePrefsManager.getAuthUser() != null)
=======
        if (route == NavRoute.Auth.route && securePrefsRepository.getAuthUser() != null)
>>>>>>> e9395b67bc9ea21fd1b762fcf0316bbe8b74d41a
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