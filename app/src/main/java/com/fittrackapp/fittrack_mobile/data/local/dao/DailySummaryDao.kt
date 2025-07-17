package com.fittrackapp.fittrack_mobile.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fittrackapp.fittrack_mobile.data.local.entity.DailySummaryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DailySummaryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(dailySummary: DailySummaryEntity)

    @Query("SELECT * FROM daily_summary WHERE date = :date")
    fun getByDate(date: String): Flow<DailySummaryEntity?>

    @Query("SELECT * FROM daily_summary ORDER BY date DESC LIMIT 1")
    fun getLast(): DailySummaryEntity?

    @Query("SELECT * FROM daily_summary ORDER BY date DESC")
    fun getAll(): Flow<List<DailySummaryEntity>>

    @Query("DELETE FROM daily_summary")
    suspend fun deleteAll()
}