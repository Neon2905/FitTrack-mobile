package com.fittrackapp.fittrack_mobile

import android.Manifest
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.annotation.RequiresPermission
import com.google.android.gms.location.ActivityRecognition
import com.google.android.gms.location.ActivityRecognitionClient

class ActivityRecognitionManager(private val context: Context) {
    private val client: ActivityRecognitionClient = ActivityRecognition.getClient(context)

    private val pendingIntent: PendingIntent by lazy {
        val intent = Intent(context, ActivityUpdatesReceiver::class.java)
        PendingIntent.getBroadcast(
            context, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

    @RequiresPermission(Manifest.permission.ACTIVITY_RECOGNITION)
    fun startActivityUpdates(intervalMillis: Long = 5000L) {
        client.requestActivityUpdates(intervalMillis, pendingIntent)
    }

    @RequiresPermission(Manifest.permission.ACTIVITY_RECOGNITION)
    fun stopActivityUpdates() {
        client.removeActivityUpdates(pendingIntent)
    }
}