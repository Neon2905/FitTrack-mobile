package com.fittrackapp.fittrack_mobile.presentation.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.DirectionsWalk
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Nightlight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.elevatedCardElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fittrackapp.fittrack_mobile.ui.theme.green
import com.fittrackapp.fittrack_mobile.utils.round

@Composable
fun TrendSection(
    viewModel: DashboardViewModel
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    // Trends
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min),
        shape = MaterialTheme.shapes.large,
        elevation = elevatedCardElevation(4.dp),
    ) {
        Column(
            Modifier
                .padding(start = 20.dp, top = 16.dp, bottom = 20.dp)
        ) {
            Text(
                "Trends",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold
                )
            )

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    TrendItem(
                        icon =
                            if (state.isCalorieTrendUp)
                                Icons.Default.KeyboardArrowUp
                            else
                                Icons.Default.KeyboardArrowDown,
                        iconColor =
                            if (state.isCalorieTrendUp)
                                green
                            else
                                MaterialTheme.colorScheme.error,
                        label = "Progress",
                        value = "${state.averageCalorieBurned.toInt()} CAL/DAY",
                        valueColor = Color(0xFF6A4FB3)
                    )
                    TrendItem(
                        icon =
                            if (state.isDistanceTrendUp)
                                Icons.Default.KeyboardArrowUp
                            else
                                Icons.Default.KeyboardArrowDown,
                        iconColor =
                            if (state.isDistanceTrendUp)
                                green
                            else
                                MaterialTheme.colorScheme.error,
                        label = "Distance",
                        value = "${(state.averageDistance).round()} KM/DAY",
                        valueColor = Color(0xFF3A80F2)
                    )
                }
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    TrendItem(
                        icon = Icons.Default.Nightlight,
                        label = "Sleep",
                        value = "6H/DAY",
                        valueColor = Color(0xFF6A4FB3)
                    )
                    TrendItem(
                        icon = Icons.AutoMirrored.Filled.DirectionsWalk,
                        label = "Steps",
                        value = "${state.averageSteps.round()}/DAY",
                        valueColor = Color(0xFF6A4FB3)
                    )
                }
            }
        }
    }
}


@Composable
fun TrendItem(
    icon: ImageVector,
    iconColor: Color = Color.Unspecified,
    label: String,
    value: String,
    valueColor: Color
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = iconColor,
            modifier = Modifier.size(35.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Column {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Bold
                )
            )
            Text(
                text = value,
                color = valueColor,
                style = MaterialTheme.typography.labelLarge.copy(
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start
                )
            )
        }
    }
}