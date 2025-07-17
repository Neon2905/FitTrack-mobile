package com.fittrackapp.fittrack_mobile

import android.Manifest
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.annotation.RequiresPermission
import com.fittrackapp.fittrack_mobile.auto_task.receiver.ActivityUpdatesReceiver
import com.google.android.gms.location.ActivityRecognition
import com.google.android.gms.location.ActivityRecognitionClient

class ActivityRecognitionManager(private val context: Context) {

    private val client: ActivityRecognitionClient = ActivityRecognition.getClient(context)

    private val pendingIntent: PendingIntent by lazy {
        val intent = Intent(context, ActivityUpdatesReceiver::class.java)
        PendingIntent.getBroadcast(
            context,
            1001,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

    @RequiresPermission(Manifest.permission.ACTIVITY_RECOGNITION)
    fun startUpdates(intervalMillis: Long = 5000L) {
        client.requestActivityUpdates(intervalMillis, pendingIntent)
            .addOnSuccessListener {
                // TODO:
                Log.d("ActivityRecognition", "Started activity updates")
            }
            .addOnFailureListener {
                Log.e("ActivityRecognition", "Failed to start updates", it)
            }
    }

    @RequiresPermission(Manifest.permission.ACTIVITY_RECOGNITION)
    fun stopUpdates() {
        client.removeActivityUpdates(pendingIntent)
            .addOnSuccessListener {
                Log.d("ActivityRecognition", "Stopped activity updates")
            }
    }
}