package com.fittrackapp.fittrack_mobile.presentation.activity

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// TODO: FIX THIS CARP!
@Composable
fun ActivityScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Activity Progress",
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(16.dp))
        CircularProgressChart(
            progress = 0.7f,
            modifier = Modifier.fillMaxWidth(),
            size = 200f,
            strokeWidth = 20f,
            backgroundColor = Color.LightGray,
            progressColor = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
fun CircularProgressChart(
    progress: Float, // Value between 0f and 1f
    modifier: Modifier = Modifier,
    size: Float = 200f,
    strokeWidth: Float = 20f,
    backgroundColor: Color = Color.LightGray,
    progressColor: Color = MaterialTheme.colorScheme.primary
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.size(size.dp)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val canvasSize = size
            val stroke = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            val diameter = canvasSize - strokeWidth
            val topLeftOffset = Offset(strokeWidth / 2, strokeWidth / 2)
            val size = Size(diameter, diameter)

            // Draw background circle
            drawArc(
                color = backgroundColor,
                startAngle = 0f,
                sweepAngle = 360f,
                useCenter = false,
                topLeft = topLeftOffset,
                size = size,
                style = stroke
            )

            // Draw progress arc
            drawArc(
                color = progressColor,
                startAngle = -90f,
                sweepAngle = 360f * progress,
                useCenter = false,
                topLeft = topLeftOffset,
                size = size,
                style = stroke
            )
        }

        // Display progress percentage
        Text(
            text = "${(progress * 100).toInt()}%",
            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 24.sp)
        )
    }
}
