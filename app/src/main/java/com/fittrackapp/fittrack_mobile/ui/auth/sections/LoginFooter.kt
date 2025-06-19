package com.fittrackapp.fittrack_mobile.ui.auth

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle

@Composable
fun LoginFooter() {
    Text(
        text = buildAnnotatedString {
            append("By signing in, you agree to the ")
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                append("Terms of Service")
            }
            append(" and ")
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                append("Data Processing Agreement")
            }
        },
        style = MaterialTheme.typography.labelSmall,
        textAlign = TextAlign.Center,
    )
}