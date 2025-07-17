package com.fittrackapp.fittrack_mobile.auto_task.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.fittrackapp.fittrack_mobile.FitTrackMobile
import com.fittrackapp.fittrack_mobile.auto_task.worker.DailySummaryWorker
import com.fittrackapp.fittrack_mobile.auto_task.worker.SyncDataWorker
import com.fittrackapp.fittrack_mobile.data.local.SecurePrefsManager
import com.fittrackapp.fittrack_mobile.domain.model.AuthUser
import kotlinx.coroutines.runBlocking

class AutoTaskReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action
        Log.i("AutoTaskReceiver", "Received action: $action")

        // TODO: This ain't detecting
        // This block only works for dynamically registered receivers.
        // Register this receiver in your Activity or Service using context.registerReceiver().
        if (action == ConnectivityManager.CONNECTIVITY_ACTION) {
            if (isConnected(context)) {
                val workRequest = OneTimeWorkRequestBuilder<SyncDataWorker>()
                    .build()
                WorkManager.getInstance(context).enqueue(workRequest)
                Log.i("AutoTaskReceiver", "Triggered API request due to $action")
            }
        }

        if (action == Intent.ACTION_BOOT_COMPLETED) {

            val summaryWorkRequest = OneTimeWorkRequestBuilder<DailySummaryWorker>()
                .build()
            WorkManager.getInstance(context).enqueue(summaryWorkRequest)

            if (isConnected(context)) {
                // Trigger Data Sync
                val workRequest = OneTimeWorkRequestBuilder<SyncDataWorker>()
                    .build()
                WorkManager.getInstance(context).enqueue(workRequest)
            }
        }
    }

    private fun isConnected(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = cm.activeNetwork ?: return false
        val capabilities = cm.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}