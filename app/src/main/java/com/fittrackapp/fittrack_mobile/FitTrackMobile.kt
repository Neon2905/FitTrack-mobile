package com.fittrackapp.fittrack_mobile

import android.Manifest
import android.app.Application
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import androidx.core.content.ContextCompat
import com.fittrackapp.fittrack_mobile.auto_task.utils.scheduleDailySummaryWorker
import com.fittrackapp.fittrack_mobile.data.local.dao.ActivityDao
import com.fittrackapp.fittrack_mobile.data.local.entity.ActivityEntity
import com.fittrackapp.fittrack_mobile.data.local.entity.ActivityType
import com.fittrackapp.fittrack_mobile.data.local.prefs.SharedPrefsManager
import com.fittrackapp.fittrack_mobile.domain.repository.ActivityRepository
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import com.fittrackapp.fittrack_mobile.data.local.prefs.PrefKeys
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlin.jvm.java

@HiltAndroidApp
class FitTrackMobile : Application() {

    @Inject
    lateinit var activityRepository: ActivityRepository

    @Inject
    lateinit var activityDao: ActivityDao

    companion object {
        lateinit var instance: FitTrackMobile
            private set
    }

    @RequiresApi(Build.VERSION_CODES.S)
    @RequiresPermission(Manifest.permission.SCHEDULE_EXACT_ALARM)
    override fun onCreate() {
        super.onCreate()
        val prefsManager =
            EntryPointAccessors.fromApplication(this, SharedPrefsEntryPoint::class.java)
                .sharedPrefsManager()
        PrefKeys.init(prefsManager)

        instance = this
        try {
            val alarmManager = getSystemService(ALARM_SERVICE) as android.app.AlarmManager
            if (alarmManager.canScheduleExactAlarms()) {
                scheduleDailySummaryWorker(this)
            }
        } catch (e: SecurityException) {
            // Handle the case where exact alarms cannot be scheduled (e.g., log or notify user)
        }

        val activityManager = ActivityRecognitionManager(applicationContext)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q &&
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACTIVITY_RECOGNITION) == PackageManager.PERMISSION_GRANTED
        ) {
            activityManager.startUpdates()
        }


        // TODO: Remove this in production
//        val activities: List<ActivityEntity> = (0..364).flatMap { day ->
//            val count = (0..2).random() // 0 to 2 activities per day
//            (1..count).map {
//                ActivityEntity(
//                    type = when ((0..3).random()) {
//                        0 -> ActivityType.WALKING
//                        1 -> ActivityType.RUNNING
//                        2 -> ActivityType.CYCLING
//                        3 -> ActivityType.WEIGHTLIFTING
//                        else -> ActivityType.UNKNOWN
//                    },
//                    startTime = java.util.Date(System.currentTimeMillis() - (day * 24 * 60 * 60 * 1000) + (it * 3600 * 1000)),
//                    endTime = null,
//                    duration = (30 + (0..30).random()) * 60 * 1000L, // Random duration between 30 and 60 minutes
//                    distance = (100 + (0..900).random()).toDouble(), // Random distance between 100m and 1km
//                    caloriesBurned = (50 + (0..150).random()).toDouble(), // Random calories burned between 50 and 200
//                    steps = (1000 + (0..5000).random()), // Random steps between 1000 and 6000
//                    reps = if ((0..1).random() == 1) (5 + (0..15).random()) else 0, // Random reps if weightlifting activity
//                    tracks = emptyList(),
//                )
//            }
//        }
//
//        runBlocking {
//            activityDao.deleteAll()
//
//            activities.forEach { activity ->
//                activityDao.add(activity)
//            }
//        }
    }
}


@EntryPoint
@InstallIn(SingletonComponent::class)
interface SharedPrefsEntryPoint {
    fun sharedPrefsManager(): SharedPrefsManager
}