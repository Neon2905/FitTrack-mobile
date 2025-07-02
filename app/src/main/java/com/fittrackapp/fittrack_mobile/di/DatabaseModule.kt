package com.fittrackapp.fittrack_mobile.di

import android.content.Context
import androidx.room.Room
import com.fittrackapp.fittrack_mobile.data.local.AppDatabase
import com.fittrackapp.fittrack_mobile.data.local.dao.ActivityDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "app_db").build()

    @Provides
    fun provideUserDao(db: AppDatabase): ActivityDao = db.activityDao()
}