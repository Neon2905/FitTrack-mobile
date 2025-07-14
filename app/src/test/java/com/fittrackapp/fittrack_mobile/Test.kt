package com.fittrackapp.fittrack_mobile

import android.util.Log
import com.fittrackapp.fittrack_mobile.data.local.dao.ActivityDao
import com.fittrackapp.fittrack_mobile.data.local.entity.ActivityEntity
import com.fittrackapp.fittrack_mobile.data.local.entity.ActivityType
import com.fittrackapp.fittrack_mobile.domain.repository.ActivityRepository
import com.fittrackapp.fittrack_mobile.presentation.SelectedPeriod
import com.fittrackapp.fittrack_mobile.utils.getStartAndEndOfPeriod
import kotlinx.coroutines.runBlocking
import org.junit.Test
import java.net.URL
import javax.inject.Inject

class Test {
    @Inject
    lateinit var activityDao: ActivityDao

    @Test
    fun test() {
        val (start, end) = SelectedPeriod.THIS_YEAR.getStartAndEndOfPeriod()

        println("Start: $start, End: $end")
    }

    @Test
    fun seedData() {

        val activities: List<ActivityEntity> = (0..364).flatMap { day ->
            val count = (0..2).random() // 0 to 2 activities per day
            (1..count).map {
                ActivityEntity(
                    type = when ((0..3).random()) {
                        0 -> ActivityType.WALKING
                        1 -> ActivityType.RUNNING
                        2 -> ActivityType.CYCLING
                        3 -> ActivityType.WEIGHTLIFTING
                        else -> ActivityType.UNKNOWN
                    },
                    startTime = java.util.Date(System.currentTimeMillis() - (day * 24 * 60 * 60 * 1000) + (it * 3600 * 1000)),
                    endTime = null,
                    duration = (30 + (0..30).random()) * 60 * 1000L, // Random duration between 30 and 60 minutes
                    distance = (100 + (0..900).random()).toDouble(), // Random distance between 100m and 1km
                    caloriesBurned = (50 + (0..150).random()).toDouble(), // Random calories burned between 50 and 200
                    steps = (1000 + (0..5000).random()), // Random steps between 1000 and 6000
                    reps = if ((0..1).random() == 1) (5 + (0..15).random()) else 0, // Random reps if weightlifting activity
                    tracks = emptyList(),
                )
            }
        }

        runBlocking {
            activities.forEach { activity ->
                activityDao.add(activity)
            }
        }
    }
}