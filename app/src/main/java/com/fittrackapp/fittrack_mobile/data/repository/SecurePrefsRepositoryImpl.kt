package com.fittrackapp.fittrack_mobile.data.repository

import com.fittrackapp.fittrack_mobile.data.local.SecurePrefsManager
import com.fittrackapp.fittrack_mobile.domain.model.AuthUser
import com.fittrackapp.fittrack_mobile.domain.repository.SecurePrefsRepository
import javax.inject.Inject

class SecurePrefsRepositoryImpl @Inject constructor(
    private val manager: SecurePrefsManager
) : SecurePrefsRepository {
    override fun saveAuthUser(user: AuthUser) = manager.saveAuthUser(user)
    override fun getAuthUser(): AuthUser? = manager.getAuthUser()
    override fun clear() = manager.clear()
}