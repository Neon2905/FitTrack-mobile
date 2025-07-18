package com.fittrackapp.fittrack_mobile.di

import com.fittrackapp.fittrack_mobile.data.local.SecurePrefsManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext

@InstallIn(SingletonComponent::class)
@Module
object SecurePrefsModule {

    @Singleton
    @Provides
    fun provideSecurePrefsManager(
        @ApplicationContext context: Context
    ): SecurePrefsManager {
        return SecurePrefsManager(context)
    }
}