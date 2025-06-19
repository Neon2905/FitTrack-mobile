package com.fittrackapp.fittrack_mobile.ui.auth

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation

@Composable
fun LoginInputFields(
    username: String,
    password: String,
    vm: AuthViewModel
) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = username,
        onValueChange = vm::onUsernameChange,
        singleLine = true,
        placeholder = { Text("Username") },
    )

    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = password,
        onValueChange = vm::onPasswordChange,
        singleLine = true,
        visualTransformation = PasswordVisualTransformation(),
        placeholder = { Text("Password") },
    )
}
