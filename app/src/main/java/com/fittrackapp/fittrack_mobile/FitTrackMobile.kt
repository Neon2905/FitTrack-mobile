package com.fittrackapp.fittrack_mobile

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class FitTrackMobile : Application() {
    companion object {
        lateinit var instance: FitTrackMobile
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}