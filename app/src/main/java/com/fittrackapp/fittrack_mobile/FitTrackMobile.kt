package com.fittrackapp.fittrack_mobile

import android.Manifest
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import androidx.core.content.ContextCompat
import androidx.work.ListenableWorker
import com.fittrackapp.fittrack_mobile.auto_task.utils.scheduleDailySummaryWorker
import com.fittrackapp.fittrack_mobile.auto_task.utils.syncData
import com.fittrackapp.fittrack_mobile.data.local.SecurePrefsManager
import com.fittrackapp.fittrack_mobile.data.local.dao.ActivityDao
import com.fittrackapp.fittrack_mobile.data.local.dao.DailySummaryDao
import com.fittrackapp.fittrack_mobile.data.local.entity.ActivityEntity
import com.fittrackapp.fittrack_mobile.data.local.entity.ActivityType
import com.fittrackapp.fittrack_mobile.data.local.entity.DailySummaryEntity
import com.fittrackapp.fittrack_mobile.data.local.prefs.SharedPrefsManager
import com.fittrackapp.fittrack_mobile.domain.repository.ActivityRepository
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import com.fittrackapp.fittrack_mobile.data.local.prefs.PrefKeys
import com.fittrackapp.fittrack_mobile.domain.repository.AuthRepository
import com.fittrackapp.fittrack_mobile.utils.formatString
import com.fittrackapp.fittrack_mobile.utils.formatToDate
import com.fittrackapp.fittrack_mobile.utils.getEndOfDay
import com.fittrackapp.fittrack_mobile.utils.getStartAndEndOfDay
import com.fittrackapp.fittrack_mobile.utils.getStartOfDay
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.time.LocalDate
import java.time.Year
import java.time.ZoneId
import java.util.Date
import kotlin.jvm.java
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.minutes

@HiltAndroidApp
class FitTrackMobile : Application() {

    @Inject
    lateinit var authRepository: AuthRepository

    @Inject
    lateinit var activityRepository: ActivityRepository

    @Inject
    lateinit var activityDao: ActivityDao

    @Inject
    lateinit var dailySummaryDao: DailySummaryDao

    @Inject
    lateinit var securePrefsManager: SecurePrefsManager

    companion object {
        lateinit var instance: FitTrackMobile
            private set
    }


    @RequiresApi(Build.VERSION_CODES.S)
    @RequiresPermission(Manifest.permission.SCHEDULE_EXACT_ALARM)
    override fun onCreate() {
        super.onCreate()

        // Initialize SecurePrefsManager
        val prefsManager =
            EntryPointAccessors.fromApplication(this, SharedPrefsEntryPoint::class.java)
                .sharedPrefsManager()
        PrefKeys.init(prefsManager)

        instance = this

        if (isConnected(this)) {
            // Check authentication and save user data
            checkAuth(authRepository, securePrefsManager)

            // Sync data on app start
            syncData(
                activityDao = activityDao,
                activityRepository = activityRepository
            )
        }

        // Daily Summary Worker scheduling
        try {
            val alarmManager = getSystemService(ALARM_SERVICE) as android.app.AlarmManager
            if (alarmManager.canScheduleExactAlarms()) {
                scheduleDailySummaryWorker(this)
            }
        } catch (e: SecurityException) {
            Log.e("FitTrackMobile", "Error scheduling daily summary worker: ${e.message}")
            // Handle SecurityException (e.g., log or notify user)
        }

        // Start Activity Recognition updates
        val activityManager = ActivityRecognitionManager(applicationContext)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q &&
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACTIVITY_RECOGNITION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            activityManager.startUpdates()
        }
    }
}

private fun isConnected(context: Context): Boolean {
    val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = cm.activeNetwork ?: return false
    val capabilities = cm.getNetworkCapabilities(network) ?: return false
    return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
}

private fun checkAuth(
    authRepository: AuthRepository,
    securePrefsManager: SecurePrefsManager
) {
    // check auth
    runBlocking {
        try {
            authRepository.checkAuth().onRight { authUser ->
                securePrefsManager.saveAuthUser(authUser)
            }
        } catch (e: Exception) {
            Log.e("AutoTaskReceiver", "Error checking auth: ${e.message}")
        }
    }
}

@EntryPoint
@InstallIn(SingletonComponent::class)
interface SharedPrefsEntryPoint {
    fun sharedPrefsManager(): SharedPrefsManager
}
