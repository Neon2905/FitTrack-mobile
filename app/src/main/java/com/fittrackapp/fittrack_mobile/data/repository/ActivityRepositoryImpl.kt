package com.fittrackapp.fittrack_mobile.data.repository

import android.util.Log
import arrow.core.Either
import com.fittrackapp.fittrack_mobile.data.local.dao.ActivityDao
import com.fittrackapp.fittrack_mobile.data.mapper.toGeneralError
import com.fittrackapp.fittrack_mobile.data.model.ActivityBulkAddRequest
import com.fittrackapp.fittrack_mobile.data.model.ActivitySyncRequest
import javax.inject.Inject
import com.fittrackapp.fittrack_mobile.data.remote.ActivityApi
import com.fittrackapp.fittrack_mobile.domain.model.Activity
import com.fittrackapp.fittrack_mobile.domain.repository.ActivityRepository
import com.fittrackapp.fittrack_mobile.domain.model.NetworkError
import com.fittrackapp.fittrack_mobile.utils.asEntity

class ActivityRepositoryImpl @Inject constructor(
    private val activityApi: ActivityApi,
) : ActivityRepository {
    override suspend fun getAll(): Either<NetworkError, List<Activity>> {
        return Either.catch {
            activityApi.getAll()
        }.mapLeft {
            Log.e("ActivityRepositoryImpl", "GetAll failed: " + it.message, it)
            it.toGeneralError()
        }
    }

    override suspend fun register(activity: Activity): Either<NetworkError, Boolean> {
        return Either.catch {
            activityApi.register(activity)
        }.mapLeft {
            Log.e("ActivityRepositoryImpl", "Register failed: " + it.message, it)
            it.toGeneralError()
        }
    }

    override suspend fun registerBulk(activities: List<Activity>): Either<NetworkError, Int> {
        return Either.catch {
            activityApi.registerBulk(ActivityBulkAddRequest(activities))
        }.mapLeft {
            Log.e("ActivityRepositoryImpl", "RegisterBulk failed: " + it.message, it)
            it.toGeneralError()
        }
    }

    override suspend fun sync(exceptions: List<Int>): Either<NetworkError, List<Activity>> {
        return Either.catch {
            activityApi.sync(exceptions.joinToString(","))
        }.mapLeft {
            Log.e("ActivityRepositoryImpl", "Sync failed: " + it.message, it)
            it.toGeneralError()
        }
    }

}