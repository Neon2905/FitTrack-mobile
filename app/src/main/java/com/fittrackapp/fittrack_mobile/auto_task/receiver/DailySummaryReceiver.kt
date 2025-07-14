package com.fittrackapp.fittrack_mobile.auto_task.receiver

import android.Manifest
import android.app.AlarmManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.fittrackapp.fittrack_mobile.auto_task.utils.scheduleDailySummaryWorker
import com.fittrackapp.fittrack_mobile.auto_task.worker.DailySummaryWorker


// TODO: This is not a valid method.
class DailySummaryReceiver : BroadcastReceiver() {
    @RequiresApi(Build.VERSION_CODES.S)
    @RequiresPermission(Manifest.permission.SCHEDULE_EXACT_ALARM)
    override fun onReceive(context: Context, intent: Intent?) {
        Log.d("DailySummaryReceiver", "Received daily summary intent")
        val request = OneTimeWorkRequestBuilder<DailySummaryWorker>().build()
        WorkManager.getInstance(context).enqueue(request)

        // Reschedule for next day
        try {
            val alarmManager =
                context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            if (alarmManager.canScheduleExactAlarms()) {
                scheduleDailySummaryWorker(context)
            } else {
                // Handle the case where exact alarms are not allowed
            }
        } catch (e: SecurityException) {
            // Handle SecurityException (e.g., log or notify user)
        }
    }
}