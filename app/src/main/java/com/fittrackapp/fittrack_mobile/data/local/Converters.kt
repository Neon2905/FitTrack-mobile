package com.fittrackapp.fittrack_mobile.data.local

import android.location.Location
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.Date
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? = value?.let { Date(it) }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? = date?.time

    @TypeConverter
    fun fromLocationList(locations: List<Location>): String {
        return Gson().toJson(locations)
    }

    @TypeConverter
    fun toLocationList(data: String): List<Location> {
        val listType = object : TypeToken<List<Location>>() {}.type
        return Gson().fromJson(data, listType)
    }
}