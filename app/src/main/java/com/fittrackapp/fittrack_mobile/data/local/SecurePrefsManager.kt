package com.fittrackapp.fittrack_mobile.data.local

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.google.gson.Gson
import com.fittrackapp.fittrack_mobile.domain.model.AuthUser
import androidx.core.content.edit

class SecurePrefsManager(context: Context) {
    private val masterKey = androidx.security.crypto.MasterKey.Builder(context)
        .setKeyScheme(androidx.security.crypto.MasterKey.KeyScheme.AES256_GCM)
        .build()
    private val prefs = EncryptedSharedPreferences.create(
        context,
        "secure_prefs",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )
    private val gson = Gson()

    fun saveAuthUser(user: AuthUser) {
        prefs.edit { putString("auth_user", gson.toJson(user)) }
    }

    fun getAuthUser(): AuthUser? {
        val json = prefs.getString("auth_user", null)
        return json?.let { gson.fromJson(it, AuthUser::class.java) }
    }

    fun clear() {
        prefs.edit { clear() }
    }
}