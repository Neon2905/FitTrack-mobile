package com.fittrackapp.fittrack_mobile.presentation.register.activity_live

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.DirectionsWalk
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.MonitorHeart
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Whatshot
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fittrackapp.fittrack_mobile.presentation.ArcProgressIndicator
import com.fittrackapp.fittrack_mobile.presentation.register.RegisterLiveActivityViewModel
import com.fittrackapp.fittrack_mobile.ui.theme.blue
import com.fittrackapp.fittrack_mobile.ui.theme.darkBlue
import com.fittrackapp.fittrack_mobile.utils.DurationUtils

@Composable
fun LiveSection(
    viewModel: RegisterLiveActivityViewModel
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Spacer(modifier = Modifier.height(5.dp))

        Box(
            modifier = Modifier
                .size(150.dp)
                .background(MaterialTheme.colorScheme.surface, shape = MaterialTheme.shapes.medium)
        ) {
            ArcProgressIndicator(
                goal = state.targetValue.toInt(),
                value = viewModel.getCurrentValue().toInt(),
                strokeWidth = 15.dp,
                size = 150.dp,
                ringColor = blue,
                trackColor = darkBlue,
                showLabel = false,
                modifier = Modifier.align(Alignment.Center)
            )


            // Target value with unit
            val targetValueWithUnit: String =
                when (state.targetType) {
                    "distance" -> "${state.targetValue / 1000} km"
                    "duration" -> "${state.targetValue.toInt()} min"
                    "calorie" -> "${state.targetValue.toInt()}" // value too large to show with unit
                    "steps" -> "${state.targetValue.toInt()}" // value too large to show with unit
                    else -> " units"
                }

            Column(
                modifier = Modifier.align(Alignment.BottomCenter),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "${(viewModel.getCurrentValue() / state.targetValue * 100).toInt()}%",
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "of $targetValueWithUnit",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.height(10.dp))
                Icon(
                    imageVector =
                        when (state.targetType) {
                            "distance" -> Icons.Default.Place
                            "duration" -> Icons.Default.AccessTime
                            "calorie" -> Icons.Default.Whatshot
                            "steps" -> Icons.AutoMirrored.Filled.DirectionsWalk
                            else -> Icons.Default.Place // Fallback icon
                        },
                    contentDescription = "Activity Icon",
                    modifier = Modifier.size(30.dp),
                    tint = MaterialTheme.colorScheme.onSurface
                )
                Spacer(Modifier.height(15.dp))
            }
        }

        // Metrics Grid
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                MetricItem(
                    icon = Icons.AutoMirrored.Filled.DirectionsWalk,
                    label = "Steps",
                    value = "${
                        if (state.steps > 0)
                            state.steps
                        else "-"
                    }",
                )
                MetricItem(
                    icon = Icons.Default.AccessTime,
                    label = "Duration",
                    value = DurationUtils.formatDuration(state.duration)
                )
                MetricItem(icon = Icons.Default.MonitorHeart, label = "bpm", value = "-")
            }
            Spacer(Modifier.height(24.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                MetricItem(
                    icon = Icons.Default.Place,
                    label = "m",
                    value = if (state.distance > 0)
                        String.format("%.2f", state.distance)
                    else "-"
                )

                MetricItem(
                    icon = Icons.Default.Whatshot,
                    label = "Cal",
                    value = "${
                        if (state.calories > 0)
                            state.calories.toInt()
                        else "-"
                    }"
                )

                val pace = if (state.distance > 0 && state.duration.inWholeSeconds > 60) {
                    val paceValue =
                        (state.duration.inWholeSeconds.toDouble() / 60) / (state.distance / 1000) // pace in min/km
                    paceValue.toInt()
                } else {
                    "-"
                }

                MetricItem(
                    icon = Icons.AutoMirrored.Filled.DirectionsWalk,
                    label = "min/km",
                    value = pace.toString()
                )
            }
        }
    }
}

@Composable
fun MetricItem(icon: ImageVector, label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = value, fontWeight = FontWeight.Bold, fontSize = 16.sp)
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                modifier = Modifier.size(14.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = label, fontSize = 12.sp)
        }
    }
}
