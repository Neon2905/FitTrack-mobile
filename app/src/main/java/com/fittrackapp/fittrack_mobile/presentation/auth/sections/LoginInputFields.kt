package com.fittrackapp.fittrack_mobile.presentation.auth

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.material3.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fittrackapp.fittrack_mobile.presentation.register.RegisterLiveActivityViewModel

@Composable
fun LoginInputFields(
    viewModel: AuthViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    // Username
    OutlinedTextField(
        value = state.username,
        onValueChange = viewModel::onUsernameChanged,
        label = { Text("Username") },
        modifier = Modifier.fillMaxWidth()
    )

    // Password
    OutlinedTextField(
        value = state.password,
        onValueChange = viewModel::onPasswordChanged,
        label = { Text("Password") },
        visualTransformation = PasswordVisualTransformation(),
        modifier = Modifier.fillMaxWidth()
    )

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Checkbox(
            checked = state.rememberMe,
            onCheckedChange = viewModel::onRemeberMeToggled
        )
        Text("Remember me")
    }

    // Login button
    Button(
        onClick = viewModel::login,
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Text(
            text =
                if (state.isSigningIn)
                    "Logging In..."
                else
                    "Login"
        )
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        HorizontalDivider(modifier = Modifier.weight(1f))
        Text("Or login with", style = MaterialTheme.typography.labelMedium)
        HorizontalDivider(modifier = Modifier.weight(1f))
    }

    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxWidth()
    ) {
        OutlinedButton(
            onClick = { /* TODO */ },
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp)
        ) {
            Text("Google")
        }

        OutlinedButton(
            onClick = { /* TODO */ },
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp)
        ) {
            Text("Facebook")
        }
    }

    Row {
        Text(text = "Don't have an account?")
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = "Sign Up",
            color = Color.Blue,
            modifier = Modifier.clickable {
                viewModel.onIsOnSignInChanged(false)
            }
        )
    }
}
