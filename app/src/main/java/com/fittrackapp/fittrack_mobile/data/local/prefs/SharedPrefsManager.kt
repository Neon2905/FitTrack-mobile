package com.fittrackapp.fittrack_mobile.data.local.prefs

import android.content.SharedPreferences
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import androidx.core.content.edit

class SharedPrefsManager(private val sharedPreferences: SharedPreferences) {

    fun putDouble(key: String, value: Double) {
        sharedPreferences.edit { putString(key, value.toString()) }
    }

    fun getDouble(key: String, defaultValue: Double = 0.0): Double {
        return sharedPreferences.getString(key, null)?.toDoubleOrNull() ?: defaultValue
    }

    fun putInt(key: String, value: Int) {
        sharedPreferences.edit { putInt(key, value) }
    }

    fun getInt(key: String, defaultValue: Int = 0): Int {
        return sharedPreferences.getInt(key, defaultValue)
    }

    fun putBoolean(key: String, value: Boolean) {
        sharedPreferences.edit { putBoolean(key, value) }
    }

    fun getBoolean(key: String, defaultValue: Boolean = false): Boolean {
        return sharedPreferences.getBoolean(key, defaultValue)
    }

    // Flow to listen for changes on a key (generic for String values)
    fun stringFlow(key: String, defaultValue: String? = null): Flow<String?> = callbackFlow {
        val listener = SharedPreferences.OnSharedPreferenceChangeListener { _, changedKey ->
            if (changedKey == key) trySend(sharedPreferences.getString(key, defaultValue))
        }
        trySend(sharedPreferences.getString(key, defaultValue))
        sharedPreferences.registerOnSharedPreferenceChangeListener(listener)
        awaitClose { sharedPreferences.unregisterOnSharedPreferenceChangeListener(listener) }
    }.distinctUntilChanged()

    // Specialized Double flow
    fun doubleFlow(key: String, defaultValue: Double = 0.0): Flow<Double> =
        stringFlow(key, defaultValue.toString())
            .map { it?.toDoubleOrNull() ?: defaultValue }
            .distinctUntilChanged()
}
