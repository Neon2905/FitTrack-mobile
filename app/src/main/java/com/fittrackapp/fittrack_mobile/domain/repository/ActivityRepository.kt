package com.fittrackapp.fittrack_mobile.domain.repository

import arrow.core.Either
import com.fittrackapp.fittrack_mobile.data.model.ActivitySyncRequest
import com.fittrackapp.fittrack_mobile.domain.model.Activity
import com.fittrackapp.fittrack_mobile.domain.model.AuthUser
import com.fittrackapp.fittrack_mobile.domain.model.NetworkError

interface ActivityRepository {
    suspend fun getAll(): Either<NetworkError, List<Activity>>
    suspend fun register(activity: Activity): Either<NetworkError, Boolean>

    suspend fun registerBulk(activities: List<Activity>): Either<NetworkError, Int>

    suspend fun sync(exceptions: List<Int>): Either<NetworkError, List<Activity>>
}