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
fun ProfileGoalsScreen(
    userName: String,
    goalSteps: Int,
    goalCalories: Int,
    onSaveGoals: (Int, Int) -> Unit
) {
    var stepsGoal by remember { mutableStateOf(goalSteps) }
    var caloriesGoal by remember { mutableStateOf(goalCalories) }

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
                Text(text = "Profile & Goals", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color(0xFF222222))
                Spacer(modifier = Modifier.height(24.dp))
                Text(text = "User: $userName", fontSize = 20.sp, color = Color(0xFF007AFF))
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = stepsGoal.toString(),
                    onValueChange = { stepsGoal = it.toIntOrNull() ?: goalSteps },
                    label = { Text("Daily Steps Goal") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = caloriesGoal.toString(),
                    onValueChange = { caloriesGoal = it.toIntOrNull() ?: goalCalories },
                    label = { Text("Daily Calories Goal") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp)
                )
                Spacer(modifier = Modifier.height(32.dp))
                Button(
                    onClick = { onSaveGoals(stepsGoal, caloriesGoal) },
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF34C759))
                ) {
                    Text("Save Goals", fontSize = 18.sp, color = Color.White)
                }
            }
        }
    }
}
