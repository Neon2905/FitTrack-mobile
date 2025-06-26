package com.fittrackapp.fittrack_mobile

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.android.gms.location.ActivityRecognitionResult
import com.google.android.gms.location.DetectedActivity

class ActivityUpdatesReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val result = ActivityRecognitionResult.extractResult(intent) ?: return
        for (activity in result.probableActivities) {
            when (activity.type) {
                DetectedActivity.WALKING -> Log.d("ActivityUpdates", "Walking detected")
                DetectedActivity.RUNNING -> Log.d("ActivityUpdates", "Running detected")
                DetectedActivity.ON_BICYCLE -> Log.d("ActivityUpdates", "Cycling detected")
                else -> { /* Ignore other activities */ }
            }
        }
    }
}