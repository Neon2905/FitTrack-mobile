package com.fittrackapp.fittrack_mobile.auto_task.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.fittrackapp.fittrack_mobile.FitTrackMobile
import com.fittrackapp.fittrack_mobile.auto_task.utils.syncData
import com.fittrackapp.fittrack_mobile.data.local.dao.ActivityDao
import com.fittrackapp.fittrack_mobile.data.model.ActivitySyncRequest
import com.fittrackapp.fittrack_mobile.domain.model.ApiError
import com.fittrackapp.fittrack_mobile.domain.repository.ActivityRepository
import com.fittrackapp.fittrack_mobile.utils.Toast
import com.fittrackapp.fittrack_mobile.utils.asActivity
import com.fittrackapp.fittrack_mobile.utils.asEntity
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking

class SyncDataWorker(
    context: Context,
    workerParams: WorkerParameters,
) : Worker(context, workerParams) {

    override fun doWork(): Result {
        val activityRepository = FitTrackMobile.instance.activityRepository
        val activityDao = FitTrackMobile.instance.activityDao
        syncData(
            activityDao = activityDao,
            activityRepository = activityRepository
        )

        return Result.success()
    }
}
