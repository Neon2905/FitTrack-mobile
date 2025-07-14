package com.fittrackapp.fittrack_mobile.auto_task.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.android.gms.location.ActivityRecognitionResult
import com.google.android.gms.location.DetectedActivity

class ActivityUpdatesReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Log.d("ActivityUpdatesReceiver", "Received activity update intent: $intent")
        val result = ActivityRecognitionResult.extractResult(intent) ?: return
        result.probableActivities.forEach {
            when (it.type) {
                DetectedActivity.WALKING -> Log.d("ActivityUpdates", "🚶 Walking")
                DetectedActivity.RUNNING -> Log.d("ActivityUpdates", "🏃 Running")
                DetectedActivity.ON_BICYCLE -> Log.d("ActivityUpdates", "🚴 Cycling")
                DetectedActivity.IN_VEHICLE -> Log.d("ActivityUpdates", "🚗 In Vehicle")
                else -> Log.d("ActivityUpdates", "Other: ${it.type}")
            }
        }
    }
}
