package com.fittrackapp.fittrack_mobile.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fittrackapp.fittrack_mobile.data.local.entity.ChallengeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ChallengeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(challenge: ChallengeEntity)

    @Query("SELECT * FROM challenge WHERE id = :id")
    fun get(id: Int): Flow<ChallengeEntity?>

    @Query("SELECT * FROM challenge")
    fun getAll(): Flow<List<ChallengeEntity>>

    @Query("DELETE FROM activity")
    suspend fun deleteAll()
}