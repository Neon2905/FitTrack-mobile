package com.fittrackapp.fittrack_mobile

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.fittrackapp.fittrack_mobile.utils.navigation.AppNavHost
import com.fittrackapp.fittrack_mobile.presentation.NavBar
import com.fittrackapp.fittrack_mobile.presentation.theme.FitTrackMobileTheme
import com.fittrackapp.fittrack_mobile.utils.Toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FitTrackMobileTheme {
                val navController = rememberNavController()

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