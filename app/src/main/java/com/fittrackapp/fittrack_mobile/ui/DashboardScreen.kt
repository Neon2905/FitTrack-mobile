package com.fittrackapp.fittrack_mobile.ui


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DashboardScreen(
    steps: Int,
    calories: Int,
    minutes: Int,
    onViewProgress: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F7))
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(24.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Activity Rings (simulated with colored circles)
            Row(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .height(120.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .background(Color(0xFF34C759), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "$steps", color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                }
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .background(Color(0xFFFF3B30), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "$calories", color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                }
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .background(Color(0xFF007AFF), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "$minutes", color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
            Card(
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(6.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Today's Summary", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color(0xFF222222))
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Steps: $steps", fontSize = 18.sp, color = Color(0xFF34C759))
                    Text(text = "Calories: $calories", fontSize = 18.sp, color = Color(0xFFFF3B30))
                    Text(text = "Minutes: $minutes", fontSize = 18.sp, color = Color(0xFF007AFF))
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
            Button(
                onClick = onViewProgress,
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF34C759))
            ) {
                Text(text = "View Progress", fontSize = 18.sp, color = Color.White)
            }
        }
    }
}
