package com.fittrackapp.fittrack_mobile.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.fittrackapp.fittrack_mobile.data.local.dao.ActivityDao
import com.fittrackapp.fittrack_mobile.data.local.dao.ChallengeDao
import com.fittrackapp.fittrack_mobile.data.local.dao.DailySummaryDao
import com.fittrackapp.fittrack_mobile.data.local.entity.ActivityEntity
import com.fittrackapp.fittrack_mobile.data.local.entity.ChallengeEntity
import com.fittrackapp.fittrack_mobile.data.local.entity.DailySummaryEntity

@Database(
    entities =
        [
            ActivityEntity::class,
            ChallengeEntity::class,
            DailySummaryEntity::class
        ],
    version = 5,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun activityDao(): ActivityDao
    abstract fun challengeDao(): ChallengeDao
    abstract fun dailySummaryDao(): DailySummaryDao
}