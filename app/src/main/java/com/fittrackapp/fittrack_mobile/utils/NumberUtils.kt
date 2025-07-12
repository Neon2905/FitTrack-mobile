package com.fittrackapp.fittrack_mobile.utils

fun Number.toFormattedString(): String {
    return "%,.1f".format(this.toDouble())
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