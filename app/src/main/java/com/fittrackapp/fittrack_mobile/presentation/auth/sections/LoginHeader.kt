package com.fittrackapp.fittrack_mobile.presentation.auth

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight

@Composable
fun LoginHeader() {
    Text("Login", style = MaterialTheme.typography.headlineLarge.copy(
        fontWeight = FontWeight.Bold,
    ))
}