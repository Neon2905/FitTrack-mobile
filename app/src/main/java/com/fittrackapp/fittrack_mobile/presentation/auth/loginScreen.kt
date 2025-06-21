package com.fittrackapp.fittrack_mobile.presentation.auth

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.background
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle


@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun LoginScreen() {
    val viewModel: AuthViewModel = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surfaceVariant),
        contentAlignment = Alignment.BottomCenter,
    ) {
        LoginSurface {
            LoginHeader()
            LoginInputFields(
                state.username,
                state.password,
                onUsernameChanged = viewModel::onUsernameChanged,
                onPasswordChanged = viewModel::onPasswordChanged
            )

            ElevatedButton(
                onClick = viewModel::login,
                modifier = Modifier
                    .fillMaxWidth(),
                shape = MaterialTheme.shapes.medium,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                ),
            ) {
                if (state.isSigningIn) {
                    Text("Signing in...")
                } else {
                    Text("Login")
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                HorizontalDivider(modifier = Modifier.weight(1f))
                Text("Or login with", style = MaterialTheme.typography.labelMedium)
                HorizontalDivider(modifier = Modifier.weight(1f))
            }

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                SocialButton("Google") { /* TODO */ }
                SocialButton("Facebook") { /* TODO */ }
            }
            LoginFooter()
        }
    }
}