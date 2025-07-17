package com.fittrackapp.fittrack_mobile.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush.Companion.linearGradient
import androidx.compose.ui.graphics.Brush.Companion.radialGradient
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RadialGradient
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fittrackapp.fittrack_mobile.presentation.auth.AuthSurface
import com.fittrackapp.fittrack_mobile.R
import com.patrykandpatrick.vico.compose.component.shape.shader.horizontalGradient

@Composable
fun AuthScreen(viewModel: AuthViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val backgroundBrush = radialGradient(
        colors = listOf(
            Color(0xFF1052D5),
            Color(0xFF3BFA4D)
        ),
        center = Offset(500f, 750f),
        radius = 1000f
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = backgroundBrush
            )
    ) {
        Column(
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier
                .fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = "Auth Image"
                )
            }
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