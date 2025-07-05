package com.fittrackapp.fittrack_mobile.data.local

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.google.gson.Gson
import com.fittrackapp.fittrack_mobile.domain.model.AuthUser
import androidx.core.content.edit
import okhttp3.Cookie
import okhttp3.HttpUrl

class SecurePrefsManager(context: Context) {
    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()
    private val prefs = EncryptedSharedPreferences.create(
        context,
        "secure_prefs",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )
    private val gson = Gson()

    fun saveCookies(cookies: List<Cookie>) {
        val serialized = cookies.joinToString("||") { it.toString() }
        prefs.edit { putString("cookies", serialized) }
    }

    fun getCookies(url: HttpUrl): List<Cookie> {
        val raw = prefs.getString("cookies", null) ?: return emptyList()

        return raw.split("||")
            .mapNotNull { Cookie.parse(url, it) }
            .filter { it.matches(url) }  // Ensures only matching cookies are returned
    }

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