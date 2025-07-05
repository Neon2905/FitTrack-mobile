package com.fittrackapp.fittrack_mobile.presentation.setting

import androidx.lifecycle.ViewModel
<<<<<<< HEAD
import com.fittrackapp.fittrack_mobile.data.local.SecurePrefsManager
import com.fittrackapp.fittrack_mobile.domain.repository.AuthRepository
=======
import com.fittrackapp.fittrack_mobile.domain.repository.AuthRepository
import com.fittrackapp.fittrack_mobile.domain.repository.SecurePrefsRepository
>>>>>>> e9395b67bc9ea21fd1b762fcf0316bbe8b74d41a
import com.fittrackapp.fittrack_mobile.navigation.NavRoute
import com.fittrackapp.fittrack_mobile.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
<<<<<<< HEAD
    private val securePrefsManager: SecurePrefsManager
) : ViewModel() {
    fun logout() {
        securePrefsManager.clear()
=======
    private val securePrefsRepository: SecurePrefsRepository
) : ViewModel() {
    fun logout() {
        securePrefsRepository.clear()
>>>>>>> e9395b67bc9ea21fd1b762fcf0316bbe8b74d41a
        Navigator.navigate(NavRoute.Auth.route)
    }
}