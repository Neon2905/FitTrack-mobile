package com.fittrackapp.fittrack_mobile.presentation.auth

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fittrackapp.fittrack_mobile.domain.repository.AuthRepository
import com.fittrackapp.fittrack_mobile.utils.Toast
import com.fittrackapp.fittrack_mobile.utils.navigation.NavRoute
import com.fittrackapp.fittrack_mobile.utils.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    // TODO: remove hardcoded values later
    private val _state = MutableStateFlow(AuthViewState(username = "username", password = "password"))
    val state = _state.asStateFlow()

    fun onUsernameChanged(value: String) {
        _state.value = _state.value.copy(username = value)
    }

    fun onPasswordChanged(value: String) {
        _state.value = _state.value.copy(password = value)
    }

    fun login() {
        viewModelScope.launch {
            _state.update { it.copy(isSigningIn = true) }

            authRepository.login(state.value.username, state.value.password)
                .onRight { authUser ->
                    Log.i("AuthViewModel", "Login successful: $authUser")
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
}