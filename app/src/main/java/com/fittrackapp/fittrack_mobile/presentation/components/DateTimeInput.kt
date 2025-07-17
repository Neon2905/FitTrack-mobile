package com.fittrackapp.fittrack_mobile.presentation

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.AccessTime
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun DateTimeInput(
    dateTime: LocalDateTime,
    onChange: (LocalDateTime) -> Unit
) {
    val context = LocalContext.current
    val dateFormatter = remember { DateTimeFormatter.ofPattern("dd MMM yyyy") }
    val timeFormatter = remember { DateTimeFormatter.ofPattern("hh:mm a") }

    val dateStr = remember(dateTime) { dateTime.format(dateFormatter) }
    val timeStr = remember(dateTime) { dateTime.format(timeFormatter) }

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("Date & Time", style = MaterialTheme.typography.labelLarge)

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            OutlinedButton(onClick = {
                val picker = DatePickerDialog(
                    context,
                    { _, year, month, dayOfMonth ->
                        val updatedDate = dateTime
                            .withYear(year)
                            .withMonth(month + 1)
                            .withDayOfMonth(dayOfMonth)
                        onChange(updatedDate)
                    },
                    dateTime.year,
                    dateTime.monthValue - 1,
                    dateTime.dayOfMonth
                )
                picker.show()
            }) {
                Icon(Icons.Default.DateRange, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(dateStr)
            }

            OutlinedButton(onClick = {
                val picker = TimePickerDialog(
                    context,
                    { _, hour, minute ->
                        val updatedTime = dateTime
                            .withHour(hour)
                            .withMinute(minute)
                        onChange(updatedTime)
                    },
                    dateTime.hour,
                    dateTime.minute,
                    false
                )
                picker.show()
            }) {
                Icon(Icons.Default.AccessTime, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(timeStr)
            }
        }
    }
}
