package com.fittrackapp.fittrack_mobile.presentation.statistic

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.elevatedCardElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fittrackapp.fittrack_mobile.presentation.StatisticsViewModel
import com.fittrackapp.fittrack_mobile.presentation.register.LogActivityViewModel
import com.fittrackapp.fittrack_mobile.presentation.statistic.sections.ChangeDailyGoalSheet
import com.fittrackapp.fittrack_mobile.sheet.BottomSheetController
import com.fittrackapp.fittrack_mobile.ui.theme.RedPink
import com.fittrackapp.fittrack_mobile.utils.toFormattedString

@Composable
fun DailyStatisticCard(
    viewModel: StatisticsViewModel
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Card(
        elevation = elevatedCardElevation(8.dp),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Column {
                        Text(
                            text = "Move",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Text(
                            text = "${state.currentSummary?.totalCalories?.toFormattedString()}/${state.currentSummary?.targetCalorie?.toFormattedString()} CAL",
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color(
                                    0xFF6A4C5D
                                )
                            )
                        )
                    }
                    Button(
                        onClick = {
                            BottomSheetController.show {
                                ChangeDailyGoalSheet()
                            }
                        },
                        shape = CircleShape,
                        contentPadding = PaddingValues(0.dp),
                        modifier = Modifier
                            .size(30.dp),
                        colors = ButtonColors(
                            containerColor = RedPink,
                            contentColor = Color.White,
                            disabledContainerColor = RedPink.copy(alpha = 0.5f),
                            disabledContentColor = Color.White.copy(alpha = 0.5f)
                        ),
                    ) {
                        Text(
                            text = "- +",
                            style = MaterialTheme.typography.labelLarge.copy(
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .padding(horizontal = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Bottom,

                    )
                {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        val max = state.hourlyStepData.maxOrNull() ?: 0f
                        state.hourlyStepData.forEachIndexed { index, value ->
                            val color =
                                if (index % 2 == 0) Color(0xFF00C2F2) else Color(
                                    0xFF0072BC
                                ) // light/cyan and deep blue

                            val ratio =
                                if (max == 0f)
                                    0f
                                else
                                    when (value) {
                                        max -> 1f
                                        0f -> 0f
                                        else -> (max / value)
                                    }
                            Box(
                                modifier = Modifier
                                    .width(10.dp)
                                    .height(
                                        ((80 * ratio).dp)
                                            .coerceAtLeast(
                                                4.dp
                                            )
                                    )
                                    .clip(RoundedCornerShape(3.dp))
                                    .background(color)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "0h",
                            style = MaterialTheme.typography.labelSmall
                        )
                        Text(
                            text = "4h",
                            style = MaterialTheme.typography.labelSmall
                        )
                        Text(
                            text = "8h",
                            style = MaterialTheme.typography.labelSmall
                        )
                        Text(
                            text = "12h",
                            style = MaterialTheme.typography.labelSmall
                        )
                        Text(
                            text = "16h",
                            style = MaterialTheme.typography.labelSmall
                        )
                        Text(
                            text = "20h",
                            style = MaterialTheme.typography.labelSmall
                        )
                        Text(
                            text = "24h",
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                }
            }
        }
    }
}