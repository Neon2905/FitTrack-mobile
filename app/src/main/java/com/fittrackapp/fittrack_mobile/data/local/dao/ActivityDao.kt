package com.fittrackapp.fittrack_mobile.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fittrackapp.fittrack_mobile.data.local.entity.ActivityEntity

@Dao
interface ActivityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(activity: ActivityEntity)

<<<<<<< HEAD
    @Query("SELECT * FROM activity WHERE id = :id")
=======
    @Query("SELECT * FROM users WHERE id = :id")
>>>>>>> e9395b67bc9ea21fd1b762fcf0316bbe8b74d41a
    suspend fun getUserById(id: Int): ActivityEntity?
}