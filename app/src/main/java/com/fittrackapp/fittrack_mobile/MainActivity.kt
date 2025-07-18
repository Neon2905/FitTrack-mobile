package com.fittrackapp.fittrack_mobile

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.Manifest.permission.ACTIVITY_RECOGNITION
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.fittrackapp.fittrack_mobile.data.local.SecurePrefsManager
import com.fittrackapp.fittrack_mobile.navigation.AppNavHost
import com.fittrackapp.fittrack_mobile.navigation.Navigator
import com.fittrackapp.fittrack_mobile.presentation.NavBar
import com.fittrackapp.fittrack_mobile.sheet.BottomSheetHost
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
            if (checkSelfPermission(ACTIVITY_RECOGNITION) != PERMISSION_GRANTED) {
                requestPermissions(arrayOf(ACTIVITY_RECOGNITION), 1001)
            }
            if (
                checkSelfPermission(
                    ACCESS_FINE_LOCATION
                ) != PERMISSION_GRANTED ||
                checkSelfPermission(
                    ACCESS_COARSE_LOCATION
                ) != PERMISSION_GRANTED
            ) {
                requestPermissions(
                    arrayOf(
                        ACCESS_FINE_LOCATION,
                        ACCESS_COARSE_LOCATION
                    ),
                    1002
                )
            }
        }

        enableEdgeToEdge()
        setContent {
            FitTrackMobileTheme {

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        NavBar()
                    }
                ) { innerPadding ->
                    BottomSheetHost {
                        Box(
                            Modifier.fillMaxSize()
                        ) {
//                            Image(
//                                painter = painterResource(id = R.drawable.ic_launcher_foreground),
//                                contentDescription = "Background",
//                                modifier = Modifier.fillMaxSize()
//                            )
                            AppNavHost(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = innerPadding.calculateBottomPadding())
                            )
                        }
                    }
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

