package com.fittrackapp.fittrack_mobile.ui


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
fun ActivityDetailsScreen(
    activityType: String,
    duration: Int,
    calories: Int,
    date: String
) {
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
                Text(
                    text = "Activity Details",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF222222)
                )
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = activityType,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF34C759)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Duration: $duration min",
                    fontSize = 20.sp,
                    color = Color(0xFF007AFF)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Calories: $calories",
                    fontSize = 20.sp,
                    color = Color(0xFFFF3B30)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Date: $date",
                    fontSize = 18.sp,
                    color = Color(0xFF8E8E93)
                )
            }
        }
    }
}
