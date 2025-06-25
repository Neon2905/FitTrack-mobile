package com.fittrackapp.fittrack_mobile.presentation.auth

data class AuthViewState(
    val isSigningIn: Boolean = false,
    val username: String = "",
    val password: String = "",
    val errorMessage: String? = null,
    val isRegistering: Boolean = false,
    val isOnSignIn: Boolean = true
)
