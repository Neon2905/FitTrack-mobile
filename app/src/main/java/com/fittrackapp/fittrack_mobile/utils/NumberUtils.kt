package com.fittrackapp.fittrack_mobile.utils

fun Number.toFormattedString(): String {
    val doubleValue = this.toDouble()
    return if (doubleValue % 1.0 == 0.0) {
        doubleValue.toInt().toString()
    } else {
        "%,.1f".format(doubleValue)
    }
}

fun Number.round(decimals: Int = 2): Number {
    val value = "%.${decimals}f".format(this.toDouble()).toDouble()
    return when (this) {
        is Int -> value.toInt()
        is Long -> value.toLong()
        is Double -> value.toDouble()
        is Short -> value.toInt().toShort()
        is Float -> value.toFloat()
        else -> value
    }
}