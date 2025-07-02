package com.fittrackapp.fittrack_mobile.presentation.setting

import androidx.lifecycle.ViewModel
import com.fittrackapp.fittrack_mobile.domain.repository.AuthRepository
import com.fittrackapp.fittrack_mobile.domain.repository.SecurePrefsRepository
import com.fittrackapp.fittrack_mobile.navigation.NavRoute
import com.fittrackapp.fittrack_mobile.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val securePrefsRepository: SecurePrefsRepository
) : ViewModel() {
    fun logout() {
        securePrefsRepository.clear()
        Navigator.navigate(NavRoute.Auth.route)
    }
}