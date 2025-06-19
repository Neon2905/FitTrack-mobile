package com.fittrackapp.fittrack_mobile.ui.auth

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class AuthViewModel : ViewModel() {
    var isSigningIn = mutableStateOf(false)
        private set

    var username = mutableStateOf("")
        private set

    fun onUsernameChange(new: String) {
        username.value = new;
    }

    var password = mutableStateOf("")
        private set

    fun onPasswordChange(new: String) {
        password.value = new;
    }


    fun onLoginClicked() {

    }
}