package com.fittrackapp.fittrack_mobile.auto_task.worker

import android.Manifest
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.annotation.RequiresPermission
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.fittrackapp.fittrack_mobile.auto_task.receiver.ActivityUpdatesReceiver
import com.google.android.gms.location.ActivityRecognition

class ActivityUpdateWorker(
    context: Context,
    workerParams: WorkerParameters
) : Worker(context, workerParams) {

    @RequiresPermission(Manifest.permission.ACTIVITY_RECOGNITION)
    override fun doWork(): Result {
        val client = ActivityRecognition.getClient(applicationContext)

        val intent = Intent(applicationContext, ActivityUpdatesReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            applicationContext,
            1002,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        client.requestActivityUpdates(5000L, pendingIntent) // every 5 seconds

        return Result.success()
    }
}
