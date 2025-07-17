package com.fittrackapp.fittrack_mobile.presentation.auth

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.material3.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fittrackapp.fittrack_mobile.presentation.AuthViewModel
import com.fittrackapp.fittrack_mobile.ui.theme.BluePrimary
import com.patrykandpatrick.vico.compose.component.shape.shader.horizontalGradient

@Composable
fun SignupInputFields(
    viewModel: AuthViewModel,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = state.email,
        onValueChange = viewModel::onEmailChanged,
        singleLine = true,
        placeholder = { Text("Email") },
    )

    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = state.username,
        onValueChange = viewModel::onUsernameChanged,
        singleLine = true,
        placeholder = { Text("Username") },
    )

    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = state.password,
        onValueChange = viewModel::onPasswordChanged,
        singleLine = true,
        visualTransformation = PasswordVisualTransformation(),
        placeholder = { Text("Password") },
    )

    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = state.confirmPassword,
        onValueChange = viewModel::onConfirmPasswordChanged,
        singleLine = true,
        visualTransformation = PasswordVisualTransformation(),
        placeholder = { Text("Confirm Password") },
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

    if (state.errorMessage != null) {
        Text(
            text = state.errorMessage ?: "",
            color = MaterialTheme.colorScheme.error,
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min),
            style = MaterialTheme.typography.bodySmall.copy(
                color = Color.Red,
                fontWeight = FontWeight.Bold
            ),
        )
    }

    ExtendedFloatingActionButton(
        onClick = viewModel::signup,
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        shape = MaterialTheme.shapes.large,
    ) {
        if (state.isRegistering) {
            Text("Signing Up...")
        } else {
            Text("Sign Up")
        }
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        HorizontalDivider(modifier = Modifier.weight(1f))
        Text("Or signup with", style = MaterialTheme.typography.labelMedium)
        HorizontalDivider(modifier = Modifier.weight(1f))
    }

    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        SocialButton("Google") { /* TODO */ }
        SocialButton("Facebook") { /* TODO */ }
    }

    Row {
        Text(text = "Already have an account?")
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = "Sign In",
            color = BluePrimary,
            modifier = Modifier.clickable {
                viewModel.onIsOnSignInChanged(true)
            }
        )
    }
}
