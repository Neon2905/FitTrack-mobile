package com.fittrackapp.fittrack_mobile.presentation.auth

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fittrackapp.fittrack_mobile.data.local.SecurePrefsManager
import com.fittrackapp.fittrack_mobile.domain.repository.AuthRepository
import com.fittrackapp.fittrack_mobile.domain.repository.SecurePrefsRepository
import com.fittrackapp.fittrack_mobile.utils.Toast
import com.fittrackapp.fittrack_mobile.navigation.NavRoute
import com.fittrackapp.fittrack_mobile.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
<<<<<<< HEAD
    private val securePrefsManager: SecurePrefsManager
=======
    private val securePrefsRepository: SecurePrefsRepository
>>>>>>> e9395b67bc9ea21fd1b762fcf0316bbe8b74d41a
) : ViewModel() {

    // TODO: remove hardcoded values later
    private val _state = MutableStateFlow(AuthViewState(username = "Neon", password = "Password"))
    val state = _state.asStateFlow()

    fun onIsOnSignInChanged(value: Boolean) {
        _state.value = _state.value.copy(isOnSignIn = value)
    }

    fun onUsernameChanged(value: String) {
        _state.value = _state.value.copy(username = value)
    }

    fun onPasswordChanged(value: String) {
        _state.value = _state.value.copy(password = value)
    }

    fun onRemeberMeToggled(value: Boolean) {
        _state.value = _state.value.copy(rememberMe = value)
    }

    fun login() {
        // TODO: this line below ain't properly working
        if (state.value.isSigningIn)
            return

        viewModelScope.launch {
            _state.update { it.copy(isSigningIn = true) }

            authRepository.login(state.value.username, state.value.password)
                .onRight { authUser ->
                    Log.i("AuthViewModel", "Login successful: $authUser")
<<<<<<< HEAD
                    securePrefsManager.saveAuthUser(authUser)
=======
                    securePrefsRepository.saveAuthUser(authUser)
>>>>>>> e9395b67bc9ea21fd1b762fcf0316bbe8b74d41a
                    Navigator.navigate(NavRoute.Dashboard.route);
                }.onLeft { error ->
                    _state.update {
                        it.copy(
                            errorMessage = error.error.message
                        )
                    }
                    Toast.show(error.error.message)
                }
            _state.update { it.copy(isSigningIn = false) }
        }
    }

    fun signup() {
        if (state.value.isRegistering)
            return

        viewModelScope.launch {
            _state.update { it.copy(isRegistering = true) }

            authRepository.register(state.value.username, state.value.password)
                .onRight { authUser ->
                    Log.i("AuthViewModel", "SignUp successful: $authUser")
                    Navigator.navigate(NavRoute.Dashboard.route);
                }.onLeft { error ->
                    _state.update {
                        it.copy(
                            errorMessage = error.error.message
                        )
                    }
                    Toast.show(error.error.message)
                }

            _state.update { it.copy(isRegistering = false) }
        }
    }
}