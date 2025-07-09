package com.fittrackapp.fittrack_mobile

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.fittrackapp.fittrack_mobile.data.local.SecurePrefsManager
import com.fittrackapp.fittrack_mobile.navigation.AppNavHost
import com.fittrackapp.fittrack_mobile.navigation.Navigator
import com.fittrackapp.fittrack_mobile.presentation.NavBar
import com.fittrackapp.fittrack_mobile.ui.theme.FitTrackMobileTheme
import com.fittrackapp.fittrack_mobile.ui.theme.blue
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity() : ComponentActivity() {
    @Inject
    lateinit var securePrefsManager: SecurePrefsManager

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Navigator.init(
            securePrefsManager
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
                ) { innerPadding ->
                    AppNavHost(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = innerPadding.calculateBottomPadding())
                    )
                }

//                Scaffold(
//                    bottomBar = {
//                        NavBar()
//                    },
//                    contentWindowInsets = androidx.compose.foundation.layout.WindowInsets(
//                        0,
//                        0,
//                        0,
//                        0
//                    )
//                ) {
//                    AppNavHost()
//                }
            }
        }
    }
}