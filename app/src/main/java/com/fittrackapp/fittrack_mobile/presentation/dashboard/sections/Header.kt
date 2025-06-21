package com.fittrackapp.fittrack_mobile.presentation.dashboard

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun Header(date: LocalDate) {
    Column {
        Text(
            text = "Summary",
            style = MaterialTheme.typography.displayMedium.copy(
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.SemiBold
            )
        )
        Text(
            text = DateTimeFormatter.ofPattern("E, MMM dd").format(date),
            style = MaterialTheme.typography.titleLarge.copy(
                color = MaterialTheme.colorScheme.secondary,
                fontWeight = FontWeight.SemiBold
            )
        )
    }
}
