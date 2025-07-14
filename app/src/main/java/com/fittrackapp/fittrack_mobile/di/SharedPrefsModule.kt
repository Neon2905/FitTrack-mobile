package com.fittrackapp.fittrack_mobile.di

import android.content.Context
import android.content.SharedPreferences
import com.fittrackapp.fittrack_mobile.data.local.prefs.SharedPrefsManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SharedPrefsModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("fittrack_prefs", Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideSharedPrefsManager(sharedPreferences: SharedPreferences): SharedPrefsManager {
        return SharedPrefsManager(sharedPreferences)
    }
}
