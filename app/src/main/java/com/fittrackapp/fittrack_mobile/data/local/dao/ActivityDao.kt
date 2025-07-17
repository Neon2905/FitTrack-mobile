package com.fittrackapp.fittrack_mobile.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fittrackapp.fittrack_mobile.data.local.entity.ActivityEntity
import com.fittrackapp.fittrack_mobile.data.local.entity.ActivitySummaryEntity
import com.fittrackapp.fittrack_mobile.data.local.entity.ChallengeEntity
import com.fittrackapp.fittrack_mobile.domain.model.Activity
import kotlinx.coroutines.flow.Flow

@Dao
interface ActivityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(activity: ActivityEntity)

    @Query("SELECT * FROM activity ORDER BY startTime DESC")
    fun getAll(): Flow<List<ActivityEntity>>

    @Query("DELETE FROM activity")
    suspend fun deleteAll()

//    @Query("SELECT * FROM activity WHERE challengeId = :challengeId ORDER BY startTime DESC")
//    fun getAllByChallenge(challengeId: Int): Flow<List<ActivityEntity>>

    @Query("SELECT * FROM activity WHERE startTime >= :start AND startTime < :end ORDER BY startTime DESC")
    fun getAllByTime(start: Long, end: Long): Flow<List<ActivityEntity>>

    @Query("SELECT id, steps, distance, caloriesBurned, startTime FROM activity WHERE startTime >= :start AND startTime < :end ORDER BY startTime DESC")
    fun getSummaryByTime(start: Long, end: Long): Flow<List<ActivitySummaryEntity>>

    @Query("SELECT * FROM activity WHERE isUploaded = 0 ORDER BY startTime ASC")
    fun getAllUnSynced(): Flow<List<ActivityEntity>>
}