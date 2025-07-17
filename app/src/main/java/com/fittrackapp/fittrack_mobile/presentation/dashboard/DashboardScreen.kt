package com.fittrackapp.fittrack_mobile.presentation.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fittrackapp.fittrack_mobile.navigation.NavRoute
import com.fittrackapp.fittrack_mobile.navigation.Navigator
import com.fittrackapp.fittrack_mobile.sheet.BottomSheetController
import com.fittrackapp.fittrack_mobile.ui.theme.BluePrimary
import com.fittrackapp.fittrack_mobile.ui.theme.blue
import com.fittrackapp.fittrack_mobile.ui.theme.green
import com.fittrackapp.fittrack_mobile.ui.theme.purple
import com.fittrackapp.fittrack_mobile.utils.round

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun DashboardScreen(viewModel: DashboardViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
    ) {
        item {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Header(java.time.LocalDate.now(), viewModel.getUsername())

                OutlinedButton(
                    onClick = {
                        BottomSheetController.show {
                            ActivityLogOption(
                                onStartLive = { Navigator.navigate(NavRoute.Register.LiveActivity.route) },
                                onLogManual = { Navigator.navigate(NavRoute.Register.LogActivity.route) },
                                onDismiss = { BottomSheetController.hide() },
                            )
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp),
                    shape = RoundedCornerShape(12.dp),
                )
                {
                    Text(
                        "Start Activity",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.ExtraBold
                        ),
                        color = green
                    )
                }

                ActivityRingSection(viewModel)

                // Steps + Distance cards
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(IntrinsicSize.Min),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    DailySummarySection(
                        modifier = Modifier.weight(1f),
                        total = state.totalSteps,
                        title = "Step Count",
                        dataColor = purple,
                        hourlyData = state.hourlyStepData
                    )
                    DailySummarySection(
                        modifier = Modifier.weight(1f),
                        total = state.totalDistance,
                        title = "Total Distance",
                        dataColor = BluePrimary,
                        unit = "km",
                        hourlyData = state.hourlyDistanceData
                    )
                }

                TrendSection(
                    viewModel
                )
            }
            Spacer(Modifier.height(10.dp))
        }
    }
}
