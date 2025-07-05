package com.fittrackapp.fittrack_mobile

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.fittrackapp.fittrack_mobile.data.local.SecurePrefsManager
<<<<<<< HEAD
=======
import com.fittrackapp.fittrack_mobile.domain.repository.SecurePrefsRepository
>>>>>>> e9395b67bc9ea21fd1b762fcf0316bbe8b74d41a
import com.fittrackapp.fittrack_mobile.navigation.AppNavHost
import com.fittrackapp.fittrack_mobile.navigation.Navigator
import com.fittrackapp.fittrack_mobile.presentation.NavBar
import com.fittrackapp.fittrack_mobile.ui.theme.FitTrackMobileTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity() : ComponentActivity() {
    @Inject
<<<<<<< HEAD
    lateinit var securePrefsManager: SecurePrefsManager
=======
    lateinit var securePrefsRepository: SecurePrefsRepository
>>>>>>> e9395b67bc9ea21fd1b762fcf0316bbe8b74d41a

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Navigator.init(
<<<<<<< HEAD
            securePrefsManager
=======
            securePrefsRepository
>>>>>>> e9395b67bc9ea21fd1b762fcf0316bbe8b74d41a
        )

        // Permission check (already present)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            if (checkSelfPermission(android.Manifest.permission.ACTIVITY_RECOGNITION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(android.Manifest.permission.ACTIVITY_RECOGNITION), 1001)
            }
        }

        val activityRecognitionManager = ActivityRecognitionManager(this)
        activityRecognitionManager.startActivityUpdates()

        enableEdgeToEdge()
        setContent {
            FitTrackMobileTheme {

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        NavBar()
                    }
                ) {
                    AppNavHost()
                }
            }
        }
    }
}