package com.fittrackapp.fittrack_mobile.presentation.dashboard

import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fittrackapp.fittrack_mobile.presentation.TrendItem
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
            Header(currentDate ?: java.time.LocalDate.now())

            Spacer(modifier = Modifier.height(16.dp))

            // Activity Ring card
            Card(
                shape = RoundedCornerShape(16.dp),
                elevation = elevatedCardElevation(4.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Column(
                        modifier = Modifier
                            .padding(start = 16.dp, top = 16.dp)
                    ) {
                        Text(
                            text = "Activity Ring",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                            )
                        )
                    }

                    Text(
                        modifier = Modifier
                            .padding(end = 16.dp)
                            .align(Alignment.CenterVertically),
                        text = buildAnnotatedString {
                            withStyle(
                                style = MaterialTheme.typography.labelLarge.toSpanStyle()
                                    .copy(fontWeight = FontWeight.ExtraBold)
                            ) {
                                append("Progress\n")
                            }
                            withStyle(
                                style = MaterialTheme.typography.labelLarge.toSpanStyle().copy(
                                    color = MaterialTheme.colorScheme.primary,
                                    fontWeight = FontWeight.ExtraBold
                                )
                            ) {
                                append("${state.currentActivity?.calories}/500 CAL")
                            }
                        },
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

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
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

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
        }
    }
}

