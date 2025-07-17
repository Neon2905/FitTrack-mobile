package com.fittrackapp.fittrack_mobile.auto_task.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.fittrackapp.fittrack_mobile.FitTrackMobile
import com.fittrackapp.fittrack_mobile.data.local.dao.ActivityDao
import com.fittrackapp.fittrack_mobile.data.local.entity.ActivityEntity
import com.fittrackapp.fittrack_mobile.data.local.entity.DailySummaryEntity
import com.fittrackapp.fittrack_mobile.utils.format
import com.fittrackapp.fittrack_mobile.utils.formatString
import com.fittrackapp.fittrack_mobile.utils.formatToDate
import com.fittrackapp.fittrack_mobile.utils.getEndOfDay
import com.fittrackapp.fittrack_mobile.utils.getStartAndEndOfDay
import com.fittrackapp.fittrack_mobile.utils.getStartOfDay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import java.util.Date
import kotlin.system.exitProcess
import kotlin.time.Duration.Companion.days

class DailySummaryWorker(
    context: Context,
    workerParams: WorkerParameters,
) : Worker(context, workerParams) {

    override fun doWork(): Result {

        // INFO:
        // Yes, it's a bit complicated, but I don't want to optimize again.
        // I mean, I tried but it didn't work out.
        // TIPS: If it works, don't touch it!!
        // Please, don't fix this. I spent 2 hours debugging this at the middle of the night.
        val dailySummaryDao = FitTrackMobile.instance.dailySummaryDao
        val activityDao: ActivityDao = FitTrackMobile.instance.activityDao

        runBlocking {
            Log.i("DailySummaryWorker", "Starting daily summary creation")
            try {
                // Check the latest daily summaries in dailySummaryDao
                val lastSummary: DailySummaryEntity? = dailySummaryDao.getLast()
                var startingDate: Date? = lastSummary?.date?.formatToDate("yyyy-MM-dd")

                // Get all activities from activityDao since the last summary date
                var activities: List<ActivityEntity> = emptyList()
                if (startingDate != null) {
                    // Get all activities from activityDao since the last summary date
                    activities = activityDao.getAllByTime(
                        startingDate.getStartAndEndOfDay().first,
                        Date().getEndOfDay()
                    ).first()
                } else {
                    // If no last summary, get all activities
                    activities = activityDao.getAll().first()
                    if (activities.isEmpty()) {
                        return@runBlocking Result.failure().also {
                            Log.i("DailySummaryWorker", "No activities found, exiting")
                        }
                    }
                    startingDate = activities.last().startTime
                }

                val daysBetween: List<Date> = generateSequence(startingDate) { date ->
                    val next = Date(date.time + 24 * 60 * 60 * 1000)
                    if (next.after(Date()) || next.getStartOfDay() == Date().getStartOfDay()) null else next
                }.toList()

                // Group activities by date and create a summary for each date
                val activitiesByDate: Map<String, List<ActivityEntity>> =
                    daysBetween.associate { day ->
                        val dayStart = day.getStartOfDay()
                        val filteredActivities = activities.filter { activity ->
                            activity.startTime.getStartOfDay() == dayStart
                        }
                        day.format("yyyy-MM-dd") to filteredActivities
                    }

                // Add daily summaries
                activitiesByDate.forEach { (date, activities) ->
                    dailySummaryDao.add(
                        DailySummaryEntity(
                            date = date,
                            totalCalories = activities.sumOf { activity -> activity.caloriesBurned },
                            totalSteps = activities.sumOf { activity -> activity.steps },
                            totalDistance = activities.sumOf { activity -> activity.distance },
                            totalDuration = activities.sumOf { activity -> activity.duration },
                            totalActivities = activities.size,
                            totalChallenges = activities.count { activity -> activity.challengeId != null },
                            isUploaded = false
                        )
                    )
                }
                return@runBlocking Result.failure()
            } catch (e: Exception) {
                Log.e("DailySummaryWorker", "Error creating daily summaries", e)
                return@runBlocking Result.failure()
            }

        }

        return Result.failure()
    }
}