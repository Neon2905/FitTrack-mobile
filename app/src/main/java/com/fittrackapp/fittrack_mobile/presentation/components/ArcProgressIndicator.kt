package com.fittrackapp.fittrack_mobile.presentation

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.fittrackapp.fittrack_mobile.ui.theme.DarkRedPink
import com.fittrackapp.fittrack_mobile.ui.theme.RedPink


@Composable
fun ArcProgressIndicator(
    goal: Int,
    value: Double,
    modifier: Modifier = Modifier,
    ringColor: Color = RedPink, // Apple red/pink
    trackColor: Color = DarkRedPink, // Dark ring background
    size: Dp = 140.dp,
    strokeWidth: Dp = 22.dp,
    showLabel: Boolean = true,
    title: String = "Move"
) {
    var progress: Float = value.toFloat() / goal.toFloat()
    if (value > goal)
        progress = 1f // Cap at 100% if over goal

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.size(size)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val sweep = 270f * progress // Not full 360, Apple does ~270°
            val startAngle = 135f       // Start bottom-left, for stylish feel
            val stroke = Stroke(
                width = strokeWidth.toPx(),
                cap = StrokeCap.Round
            )

            // Track background
            drawArc(
                color = trackColor,
                startAngle = startAngle,
                sweepAngle = 270f,
                useCenter = false,
                style = stroke
            )

            // Progress ring
            drawArc(
                color = ringColor,
                startAngle = startAngle,
                sweepAngle = sweep,
                useCenter = false,
                style = stroke
            )

//            // Optional: Arrow tip
//            val radius = size.toPx() / 2
//            val angleRad = Math.toRadians((startAngle + sweep).toDouble())
//            val x = center.x + cos(angleRad) * (radius - stroke.width / 2)
//            val y = center.y + sin(angleRad) * (radius - stroke.width / 2)
//            drawCircle(
//                color = ringColor,
//                radius = strokeWidth.toPx() / 3,
//                center = Offset(x.toFloat(), y.toFloat())
//            )
        }

        // Text inside ring
        if (showLabel)
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Bold
                    ),
                )
                Text(
                    text = "$value / $goal CAL",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = ringColor
                )
            }
    }
}