package com.fittrackapp.fittrack_mobile.presentation.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.DirectionsWalk
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Nightlight
import androidx.compose.material3.*
import androidx.compose.material3.CardDefaults.elevatedCardElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fittrackapp.fittrack_mobile.navigation.NavRoute
import com.fittrackapp.fittrack_mobile.navigation.Navigator
import com.fittrackapp.fittrack_mobile.presentation.TrendItem
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.rememberStartAxis
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import com.patrykandpatrick.vico.core.entry.FloatEntry

import java.time.ZoneId

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun DashboardScreen(viewModel: DashboardViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val currentDate =
        state.currentActivity?.startTime?.toInstant()?.atZone(ZoneId.systemDefault())
            ?.toLocalDate();

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(WindowInsets.systemBars.asPaddingValues())
            .padding(horizontal = 24.dp, vertical = 20.dp),
    ) {
        item {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Header(currentDate ?: java.time.LocalDate.now(), viewModel.getUsername())

                OutlinedButton(
                    onClick = {
                        Navigator.navigate(NavRoute.Register.LiveActivity.route)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp),
                    shape = RoundedCornerShape(12.dp)
                )
                {
                    Text(
                        "Start Activity",
                    )
                }

                ActivityRingCard(viewModel)

                // Steps + Distance cards
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(260.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                        shape = RoundedCornerShape(16.dp),
                        elevation = elevatedCardElevation(4.dp),
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                "Steps",
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                )
                            )
                            Text(
                                "325",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            )

                        }
                    }

                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                        shape = RoundedCornerShape(16.dp),
                        elevation = elevatedCardElevation(4.dp),
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                "Distance",
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                )
                            )
                            Text(
                                "0.10 KM",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Blue
                                )
                            )

                            val entries = listOf(
                                FloatEntry(0f, 10f),
                                FloatEntry(1f, 30f),
                                FloatEntry(2f, 20f),
                                FloatEntry(3f, 40f),
                                FloatEntry(4f, 15f)
                            )

                            val modelProducer = remember { ChartEntryModelProducer(entries) }

                            Chart(
                                chart = com.patrykandpatrick.vico.compose.chart.line.lineChart(),
                                chartModelProducer = modelProducer,
                                startAxis = rememberStartAxis(),
                                bottomAxis = rememberBottomAxis(),
                            )
                        }
                    }
                }

                // Trends
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp),
                    shape = RoundedCornerShape(16.dp),
                    elevation = elevatedCardElevation(4.dp),
                ) {
                    Column(
                        Modifier
                            .padding(start = 16.dp, top = 16.dp)
                    ) {
                        Text(
                            "Trends",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold
                            )
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        Column(
                            Modifier.padding(start = 10.dp, end = 50.dp),
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                TrendItem(
                                    icon = Icons.Default.KeyboardArrowUp,
                                    label = "Progress",
                                    value = "100CAL/DAY",
                                    valueColor = Color(0xFF6A4FB3)
                                )
                                TrendItem(
                                    icon = Icons.Default.Nightlight,
                                    label = "Sleep",
                                    value = "6H/DAY",
                                    valueColor = Color(0xFF6A4FB3)
                                )
                            }
                            Spacer(modifier = Modifier.height(12.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                TrendItem(
                                    icon = Icons.Default.KeyboardArrowUp,
                                    label = "Distance",
                                    value = "1.2MI/DAY",
                                    valueColor = Color(0xFF3A80F2)
                                )
                                TrendItem(
                                    icon = Icons.AutoMirrored.Filled.DirectionsWalk,
                                    label = "Steps",
                                    value = "300/DAY",
                                    valueColor = Color(0xFF6A4FB3)
                                )
                            }
                        }
                    }
                }

                // Spacer to push content above the bottom navigation
                Spacer(Modifier.padding(bottom = 50.dp))
            }
        }
    }
}
