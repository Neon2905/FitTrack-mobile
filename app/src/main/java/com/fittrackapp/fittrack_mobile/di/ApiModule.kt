package com.fittrackapp.fittrack_mobile.di

import com.fittrackapp.fittrack_mobile.data.local.SecurePrefsManager
import com.fittrackapp.fittrack_mobile.data.remote.ActivityApi
import com.fittrackapp.fittrack_mobile.data.remote.AppCookieJar
import com.fittrackapp.fittrack_mobile.data.remote.AuthApi
import com.fittrackapp.fittrack_mobile.utils.Constants.BASE_URL
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object ApiModule {
    // Gson instance for parsing JSON responses
    val gson =
        GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create()

    @Provides
    @Singleton
    fun provideAppCookieJar(securePrefsManager: SecurePrefsManager): AppCookieJar {
        return AppCookieJar(securePrefsManager)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(appCookieJar: AppCookieJar): OkHttpClient {
        return OkHttpClient.Builder()
            .cookieJar(appCookieJar)
            .build()
    }

    @Singleton
    @Provides
    fun provideAuthApi(okHttpClient: OkHttpClient): AuthApi {
        return Retrofit
            .Builder()
            .client(okHttpClient)
            .baseUrl("${BASE_URL}/auth/")
            // Gson parser
            .addConverterFactory(
                GsonConverterFactory.create(gson)
            )
            .build()
            .create(AuthApi::class.java)
    }

    @Singleton
    @Provides
    fun provideActivityApi(okHttpClient: OkHttpClient): ActivityApi {
        return Retrofit
            .Builder()
            .client(okHttpClient)
            .baseUrl("$BASE_URL/activity/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ActivityApi::class.java)
    }
}