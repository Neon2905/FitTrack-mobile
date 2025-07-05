package com.fittrackapp.fittrack_mobile.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.fittrackapp.fittrack_mobile.data.local.dao.ActivityDao
import com.fittrackapp.fittrack_mobile.data.local.entity.ActivityEntity

@Database(entities = [ActivityEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun activityDao(): ActivityDao
}