package com.fittrackapp.fittrack_mobile.utils

import android.util.Log
import android.util.Patterns.EMAIL_ADDRESS
import java.text.SimpleDateFormat
import java.time.Period
import java.util.Date
import java.util.Locale
import java.util.TimeZone

fun String.tryCastToInt(): Any {
    return try {
        this.toInt()
    } catch (e: NumberFormatException) {
        false
    }
}

fun String.tryCastToDouble(): Any {
    return try {
        this.toDouble()
    } catch (e: NumberFormatException) {
        false
    }
}

fun String.tryCastToLong(): Any {
    return try {
        this.toLong()
    } catch (e: NumberFormatException) {
        false
    }
}

fun String.tryCastToFloat(): Any {
    return try {
        this.toFloat()
    } catch (e: NumberFormatException) {
        false
    }
}

fun String.tryCastToBoolean(): Any {
    return when (this.lowercase()) {
        "true" -> true
        "false" -> false
        else -> false
    }
}

fun String.tryCastToPeriod(): Any {
    return try {
        Period.parse(this)
    } catch (e: Exception) {
        false
    }
}

fun String.tryCastToLocalDateTime(): Any {
    return try {
        java.time.LocalDateTime.parse(this)
    } catch (e: Exception) {
        false
    }
}

fun String.toDateOrNull(): Date? {
    val formats = listOf(
        "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
        "yyyy-MM-dd'T'HH:mm:ss'Z'",
        "yyyy-MM-dd HH:mm:ss",
        "yyyy-MM-dd",
        "dd-MM-yyyy",
        "MM/dd/yyyy",
        "dd/MM/yyyy",
        "MMM dd, yyyy",
        "dd MMM yyyy",
        "EEE, MMM dd, yyyy",
        "EEE, dd MMM yyyy HH:mm:ss zzz", // RFC1123
        "EEE MMM dd HH:mm:ss z yyyy"     // to parse Date.toString()
    )

    return formats.firstNotNullOfOrNull { format ->
        runCatching {
            SimpleDateFormat(format, Locale.getDefault()).apply {
                timeZone = TimeZone.getTimeZone("UTC")
                isLenient = false
            }.parse(this)
        }.getOrNull()
    }
}

fun String.formatToDate(format: String): Date? {
    return runCatching {
        SimpleDateFormat(format, Locale.getDefault()).apply {
            timeZone = TimeZone.getTimeZone("UTC")
            isLenient = false
        }.parse(this)
    }.getOrNull()
        .also { result ->
            if (result == null) {
                Log.e(
                    "StringUtils",
                    "Failed to format string '$this' to date with format '$format'."
                )
            }
        }
}

fun String.isValidEmail(): Boolean {
    return EMAIL_ADDRESS.matcher(this).matches()
        .also { isValid ->
            if (!isValid) {
                Log.e("StringUtils", "Invalid email format: $this")
            }
        }
}