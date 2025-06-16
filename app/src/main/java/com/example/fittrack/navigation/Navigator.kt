package com.example.fittrack.navigation

import android.annotation.SuppressLint
import androidx.navigation.NavHostController

object Navigator {
    @SuppressLint("StaticFieldLeak")
    private var navController: NavHostController? = null

    fun setController(controller: NavHostController) {
        navController = controller
    }
    fun navigate(route: String) {
        navController?.navigate(route)
    }

    fun navigateAndClear(route: String) {
        navController?.navigate(route) {
            popUpTo(0)
        }
    }

    fun goBack() {
        navController?.popBackStack()
    }
}