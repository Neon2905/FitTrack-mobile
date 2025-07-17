package com.fittrackapp.fittrack_mobile.auto_task.utils

import android.util.Log
import com.fittrackapp.fittrack_mobile.data.local.dao.ActivityDao
import com.fittrackapp.fittrack_mobile.domain.repository.ActivityRepository
import com.fittrackapp.fittrack_mobile.utils.Toast
import com.fittrackapp.fittrack_mobile.utils.asActivity
import com.fittrackapp.fittrack_mobile.utils.asEntity
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking

fun syncData(
    activityDao: ActivityDao,
    activityRepository: ActivityRepository
) {

    runBlocking {
        // Upload unsynced activities
        val unsyncedActivities = activityDao.getAllUnSynced().firstOrNull() ?: emptyList()
        Log.i("SyncDataWorker", "Found ${unsyncedActivities.size} unsynced activities")
        if (!unsyncedActivities.isEmpty()) {
            activityRepository.registerBulk(
                unsyncedActivities.map { it.asActivity() }
            ).map { success ->
                Log.i("SyncDataWorker", "Bulk sync success: $success")
                // Mark all as synced
                unsyncedActivities.forEach { activity ->
                    activityDao.add(activity.copy(isUploaded = true))
                }
            }.mapLeft {
                Log.e("SyncDataWorker", "Bulk sync failed: ${it.message}")
                Toast.show("Failed to sync activities: ${it.message}")
            }
        }

        // Get new activities from the server
        val ids: List<Int> =
            activityDao.getAll().firstOrNull()?.map { it.id } ?: emptyList()

        activityRepository.sync(
            ids
        ).onRight { activities ->
            activities.forEach { activity ->
                activityDao.add(activity.asEntity().copy(isUploaded = true))
            }
        }
    }
}