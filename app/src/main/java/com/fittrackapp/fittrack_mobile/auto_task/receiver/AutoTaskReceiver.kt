package com.fittrackapp.fittrack_mobile.auto_task.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.fittrackapp.fittrack_mobile.auto_task.worker.SyncDataWorker

class AutoTaskReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action
        Log.i("AutoTaskReceiver", "Received action: $action")

        // TODO: This ain't detecting
        if (action == "android.net.conn.CONNECTIVITY_CHANGE") {
            Log.i("AutoTaskReceiver", "Connectivity change detected: $action")
            if (isConnected(context)) {

                // Trigger Data Sync
                val workRequest = OneTimeWorkRequestBuilder<SyncDataWorker>()
                    .build()
                WorkManager.getInstance(context).enqueue(workRequest)

                Log.i("AutoTaskReceiver", "Triggered API request due to $action")
            }
        }

        if (action == Intent.ACTION_BOOT_COMPLETED) {
            Log.i("AutoTaskReceiver", "Boot complete detected: $action")

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