package com.fittrackapp.fittrack_mobile.presentation

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fittrackapp.fittrack_mobile.presentation.auth.SignupFooter
import com.fittrackapp.fittrack_mobile.presentation.auth.SignupHeader
import com.fittrackapp.fittrack_mobile.presentation.auth.SignupInputFields


@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun SignupScreen() {
    val viewModel: AuthViewModel = hiltViewModel()

    SignupHeader()
    SignupInputFields(
        viewModel = viewModel
    )

    Spacer(modifier = Modifier.height(16.dp))

    SignupFooter()
}