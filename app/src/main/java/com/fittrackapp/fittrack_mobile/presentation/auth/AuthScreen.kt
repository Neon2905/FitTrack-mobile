package com.fittrackapp.fittrack_mobile.presentation.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fittrackapp.fittrack_mobile.ui.theme.blue
import com.fittrackapp.fittrack_mobile.ui.theme.green

@Composable
fun AuthScreen(viewModel: AuthViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val backgroundBrush = androidx.compose.ui.graphics.Brush.linearGradient(
        colors = listOf(
            androidx.compose.ui.graphics.Color(0xFF1453C9),
            androidx.compose.ui.graphics.Color(0xFF458C48)
        ),
        start = androidx.compose.ui.geometry.Offset(0f, 0f),
        end = androidx.compose.ui.geometry.Offset(1000f, 1000f)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = backgroundBrush)
    ) {
        Box(
            contentAlignment = Alignment.BottomCenter,
            modifier = Modifier
                .fillMaxSize()
                .background(green)
        ) {
            AuthSurface(
                modifier = Modifier
                    .wrapContentHeight(),
            ) {
                if (state.isOnSignIn)
                    LoginScreen()
                else
                    SignupScreen()
            }
        }
    }
}