package com.fittrackapp.fittrack_mobile

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import android.app.Application
import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.fittrackapp.fittrack_mobile.data.local.AppDatabase
import com.fittrackapp.fittrack_mobile.data.local.dao.ActivityDao
import kotlinx.coroutines.flow.forEach
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    private lateinit var db: AppDatabase
    private lateinit var activityDao: ActivityDao

    @Before
    fun setUp() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext<Application>(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
        activityDao = db.activityDao()
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun testDao()= kotlinx.coroutines.runBlocking {
        val activities = activityDao.getAll()
        activities.collect {
            Log.i("TestDao", "Activity: $it")
        }
    }
}