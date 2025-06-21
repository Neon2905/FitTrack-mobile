package com.fittrackapp.fittrack_mobile.di

import com.fittrackapp.fittrack_mobile.data.repository.AuthRepositoryImpl
import com.fittrackapp.fittrack_mobile.domain.repository.AuthRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun provideAuthRepository(impl: AuthRepositoryImpl): AuthRepository

}