package com.fittrackapp.fittrack_mobile.auto_task.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.fittrackapp.fittrack_mobile.FitTrackMobile
import com.fittrackapp.fittrack_mobile.data.local.dao.ActivityDao
import com.fittrackapp.fittrack_mobile.data.model.ActivitySyncRequest
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

        // TODO:
        runBlocking {
//            // Upload unsynced activities
//            val unsyncedActivities = activityDao.getAllUnSynced().firstOrNull() ?: emptyList()
//            unsyncedActivities.forEach { activity ->
//                activityRepository.register(activity.asActivity())
//            }
//
//            // Get new activities from the server
//            val ids: List<Int> = activityDao.getAll().firstOrNull()?.map { it.id } ?: emptyList()
//            activityRepository.sync(
//                ActivitySyncRequest(
//                    ids
//                ),
//            ).onRight { activities ->
//                activities.forEach { activity ->
//                    activityDao.add(activity.asEntity())
//                }
//            }
        }

        return Result.success()
    }
}
