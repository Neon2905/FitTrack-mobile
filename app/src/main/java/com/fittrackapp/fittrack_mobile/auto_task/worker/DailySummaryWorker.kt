package com.fittrackapp.fittrack_mobile.auto_task.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.fittrackapp.fittrack_mobile.data.local.dao.ActivityDao
import com.fittrackapp.fittrack_mobile.data.local.dao.DailySummaryDao
import com.fittrackapp.fittrack_mobile.data.local.entity.ActivityEntity
import com.fittrackapp.fittrack_mobile.data.local.entity.DailySummaryEntity
import com.fittrackapp.fittrack_mobile.utils.format
import com.fittrackapp.fittrack_mobile.utils.getStartAndEndOfDay
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.first
import java.util.Date

@HiltWorker
class DailySummaryWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val dailySummaryDao: DailySummaryDao,
    private val activityDao: ActivityDao
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        Log.i("DailySummaryWorker", "Starting daily summary creation")
        return try {
            val todaySummary = createTodaySummary()
            dailySummaryDao.add(todaySummary)

            Result.success()
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure()
        }
    }

    private suspend fun createTodaySummary(): DailySummaryEntity {
        val (start, end) = Date().getStartAndEndOfDay()
        val activities: List<ActivityEntity> = activityDao.getAllByTime(start, end).first()
        return DailySummaryEntity(
            date = Date().format("EEE, MMM dd, yyyy"),
            totalCalories = activities.sumOf { it.caloriesBurned },
            totalSteps = activities.sumOf { it.steps },
            totalDistance = activities.sumOf { it.distance },
            totalDuration = activities.sumOf { it.duration },
            totalActivities = activities.size,
            totalChallenges = activities.count { it.challengeId != null },
            isUploaded = false
        )
    }
}