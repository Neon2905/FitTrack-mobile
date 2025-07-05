package com.fittrackapp.fittrack_mobile.domain.repository

import com.fittrackapp.fittrack_mobile.domain.model.AuthUser

interface SecurePrefsRepository {
    fun saveAuthUser(user: AuthUser)
    fun getAuthUser(): AuthUser?
    fun clear()
}