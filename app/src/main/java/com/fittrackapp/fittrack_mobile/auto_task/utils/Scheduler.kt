package com.fittrackapp.fittrack_mobile.auto_task.utils

import android.Manifest
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import com.fittrackapp.fittrack_mobile.auto_task.receiver.DailySummaryReceiver
import java.util.Calendar

@RequiresApi(Build.VERSION_CODES.S)
@RequiresPermission(Manifest.permission.SCHEDULE_EXACT_ALARM)
fun scheduleDailySummaryWorker(context: Context) {
    Log.d("Scheduler", "Scheduling daily summary worker")
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val intent = Intent(context, DailySummaryReceiver::class.java)
    val pendingIntent = PendingIntent.getBroadcast(
        context, 1001, intent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    val now = Calendar.getInstance()
    val target = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, 1)
        set(Calendar.MINUTE, 20)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)

        if (before(now)) {
            add(Calendar.DAY_OF_MONTH, 1) // schedule for tomorrow
        }
    }

    if (alarmManager.canScheduleExactAlarms()) {
        try {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                target.timeInMillis,
                pendingIntent
            )
        } catch (e: SecurityException) {
            // Handle or log the exception as needed
            e.printStackTrace()
        }
    }
}
