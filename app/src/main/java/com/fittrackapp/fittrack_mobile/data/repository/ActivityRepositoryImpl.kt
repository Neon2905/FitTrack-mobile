package com.fittrackapp.fittrack_mobile.data.repository

import android.util.Log
import androidx.credentials.exceptions.domerrors.NetworkError
import arrow.core.Either
import com.fittrackapp.fittrack_mobile.data.mapper.toGeneralError
import javax.inject.Inject
import com.fittrackapp.fittrack_mobile.data.remote.ActivityApi
import com.fittrackapp.fittrack_mobile.domain.model.Activity
import com.fittrackapp.fittrack_mobile.domain.repository.ActivityRepository

class ActivityRepositoryImpl @Inject constructor(
    private val activityApi: ActivityApi
) : ActivityRepository {
    override suspend fun getAll(): Either<com.fittrackapp.fittrack_mobile.domain.model.NetworkError, List<Activity>> {
        return Either.catch {
            activityApi.getAll()
        }.mapLeft {
            Log.e("ActivityRepositoryImpl", "GetAll failed: " + it.message, it)
            it.toGeneralError()
        }
    }

    override suspend fun register(activity: Activity): Either<NetworkError, Boolean> {
        TODO("Not yet implemented")
    }

}