package com.fittrackapp.fittrack_mobile.data.local

import androidx.room.TypeConverter
import java.util.Date
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? = value?.let { Date(it) }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? = date?.time
}