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

    @Query("SELECT * FROM activity")
    fun getAll(): Flow<List<ActivityEntity>>

    @Query("DELETE FROM activity")
    suspend fun deleteAll()

    @Query("SELECT * FROM activity WHERE challengeId = :challengeId")
    fun getAllByChallenge(challengeId: Int): Flow<List<ActivityEntity>>

    @Query("SELECT * FROM activity WHERE startTime >= :start AND startTime < :end")
    fun getAllByTime(start: Long, end: Long): Flow<List<ActivityEntity>>

    @Query("SELECT id, steps, distance, caloriesBurned, startTime FROM activity WHERE startTime >= :start AND startTime < :end")
    fun getSummaryByTime(start: Long, end: Long): Flow<List<ActivitySummaryEntity>>

    @Query("SELECT * FROM activity WHERE challengeId = :challengeId")
    fun getByChallengeId(challengeId: Int): Flow<List<ActivityEntity>>

    @Query("SELECT * FROM activity WHERE isUploaded = 0")
    fun getAllUnSynced(): Flow<List<ActivityEntity>>
}