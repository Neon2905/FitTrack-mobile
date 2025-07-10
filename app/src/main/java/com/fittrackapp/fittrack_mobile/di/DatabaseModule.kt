package com.fittrackapp.fittrack_mobile.di

import android.content.Context
import androidx.room.Room
import com.fittrackapp.fittrack_mobile.data.local.AppDatabase
import com.fittrackapp.fittrack_mobile.data.local.dao.ActivityDao
import com.fittrackapp.fittrack_mobile.data.local.dao.ChallengeDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Challenge
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "app_db")
            .fallbackToDestructiveMigration(true)
            .build()

    @Provides
    fun provideActivityDao(db: AppDatabase): ActivityDao = db.activityDao()

    @Provides
    fun provideChallengeDao(db: AppDatabase): ChallengeDao = db.challengeDao()
}