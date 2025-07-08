package com.fittrackapp.fittrack_mobile.presentation.auth

data class AuthViewState(
    val isSigningIn: Boolean = false,
    val email: String = "",
    val username: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val errorMessage: String? = null,
    val isRegistering: Boolean = false,
    val isOnSignIn: Boolean = true,
    val rememberMe: Boolean = false,
    val passwordError: String? = null,
    val usernameError: String? = null,
    val error: String? = null,
)
