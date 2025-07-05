package com.fittrackapp.fittrack_mobile.data.remote

import com.fittrackapp.fittrack_mobile.data.local.SecurePrefsManager
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import javax.inject.Inject

class AppCookieJar @Inject constructor(
    private val securePrefsManager: SecurePrefsManager
) : CookieJar {
    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        securePrefsManager.saveCookies(cookies)
    }

    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        return securePrefsManager.getCookies(url)
    }
}