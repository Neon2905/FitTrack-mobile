package com.fittrackapp.fittrack_mobile.ui


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun WorkoutLoggingScreen(
    onSaveWorkout: (String, Int, Int) -> Unit
) {
    var workoutType by remember { mutableStateOf("") }
    var duration by remember { mutableStateOf(0) }
    var calories by remember { mutableStateOf(0) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F7))
    ) {
        Card(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(24.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(32.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(32.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Log Workout", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color(0xFF222222))
                Spacer(modifier = Modifier.height(24.dp))
                OutlinedTextField(
                    value = workoutType,
                    onValueChange = { workoutType = it },
                    label = { Text("Workout Type") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = duration.toString(),
                    onValueChange = { duration = it.toIntOrNull() ?: 0 },
                    label = { Text("Duration (min)") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = calories.toString(),
                    onValueChange = { calories = it.toIntOrNull() ?: 0 },
                    label = { Text("Calories Burned") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp)
                )
                Spacer(modifier = Modifier.height(32.dp))
                Button(
                    onClick = { onSaveWorkout(workoutType, duration, calories) },
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF34C759))
                ) {
                    Text("Save Workout", fontSize = 18.sp, color = Color.White)
                }
            }
        }
    }
}
