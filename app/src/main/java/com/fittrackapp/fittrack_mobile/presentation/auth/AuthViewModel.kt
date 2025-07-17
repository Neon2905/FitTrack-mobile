package com.fittrackapp.fittrack_mobile.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fittrackapp.fittrack_mobile.data.local.SecurePrefsManager
import com.fittrackapp.fittrack_mobile.data.remote.AuthApi
import com.fittrackapp.fittrack_mobile.data.repository.AuthRepositoryImpl
import com.fittrackapp.fittrack_mobile.domain.repository.AuthRepository
import com.fittrackapp.fittrack_mobile.domain.repository.SecurePrefsRepository
import com.fittrackapp.fittrack_mobile.utils.Toast
import com.fittrackapp.fittrack_mobile.navigation.NavRoute
import com.fittrackapp.fittrack_mobile.navigation.Navigator
import com.fittrackapp.fittrack_mobile.utils.isValidEmail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val securePrefsManager: SecurePrefsManager
) : ViewModel() {
    private val _state = MutableStateFlow(AuthViewState())
    val state = _state.asStateFlow()

    fun onIsOnSignInChanged(value: Boolean) {
        _state.value = _state.value.copy(isOnSignIn = value)
    }

    fun onEmailChanged(value: String) {
        _state.value = _state.value.copy(email = value)
    }

    fun onUsernameChanged(value: String) {
        _state.value = _state.value.copy(username = value)
    }

    fun onPasswordChanged(value: String) {
        _state.value = _state.value.copy(password = value)
    }

    fun onConfirmPasswordChanged(value: String) {
        _state.value = _state.value.copy(confirmPassword = value)
    }

    fun onRemeberMeToggled(value: Boolean) {
        _state.value = _state.value.copy(rememberMe = value)
    }

    fun login() {
        if (state.value.isSigningIn)
            return

        _state.update {
            it.copy(
                isSigningIn = true,
                errorMessage = null
            )
        }

        viewModelScope.launch {
            _state.update { it.copy(isSigningIn = true) }

            try {
                authRepository.login(_state.value.username, _state.value.password)
                    .onRight { authUser ->
                        securePrefsManager.saveAuthUser(authUser)
                        Log.i("AuthViewModel", "Login successful: $authUser")
                        Navigator.navigate(NavRoute.Dashboard.route);
                    }.onLeft { error ->
                        Toast.show(error.error.message)
                    }
            } catch (e: Exception) {
                Log.e("AuthViewModel", "Registration failed: ${e.message}")
            }.also {
                _state.update { it.copy(isSigningIn = false) }
            }
        }
    }

    fun signup() {
        if (state.value.isRegistering)
            return

        if (!state.value.email.isValidEmail()) {
            return _state.update {
                it.copy(
                    errorMessage = (it.errorMessage ?: "") + "Please enter a valid email address.\n"
                )
            }
        }

        if (state.value.password != state.value.confirmPassword) {
            return _state.update {
                it.copy(
                    errorMessage = (it.errorMessage ?: "") + "Passwords do not match.\n"
                )
            }
        }

        viewModelScope.launch {
            _state.update {
                it.copy(
                    isRegistering = true,
                    errorMessage = null
                )
            }

            try {
                authRepository.register(
                    _state.value.email,
                    _state.value.username,
                    _state.value.password
                )
                    .onRight { authUser ->
                        securePrefsManager.saveAuthUser(authUser)
                        Log.i("AuthViewModel", "Registered successfully: $authUser")
                        Navigator.navigate(NavRoute.Dashboard.route);
                    }.onLeft { error ->
                        Toast.show(error.error.message)
                    }

            } catch (e: Exception) {
                Log.e("AuthViewModel", "Registration failed: ${e.message}")
            }.also {
                _state.update { it.copy(isRegistering = false) }
            }
        }
    }
}