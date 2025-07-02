package com.fittrackapp.fittrack_mobile.di

import android.content.Context
import com.fittrackapp.fittrack_mobile.data.local.SecurePrefsManager
import com.fittrackapp.fittrack_mobile.data.repository.SecurePrefsRepositoryImpl
import com.fittrackapp.fittrack_mobile.domain.repository.SecurePrefsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SecurePrefsModule {
    @Provides
    @Singleton
    fun provideSecurePrefsManager(@ApplicationContext context: Context) = SecurePrefsManager(context)

    @Provides
    @Singleton
    fun provideSecurePrefsRepository(manager: SecurePrefsManager): SecurePrefsRepository =
        SecurePrefsRepositoryImpl(manager)
}

@EntryPoint
@InstallIn(SingletonComponent::class)
interface SecurePrefsRepositoryEntryPoint {
    fun securePrefsRepository(): SecurePrefsRepository
}