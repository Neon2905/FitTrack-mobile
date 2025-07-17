package com.fittrackapp.fittrack_mobile.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardDefaults.elevatedCardElevation
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fittrackapp.fittrack_mobile.data.local.entity.DailySummaryEntity
import com.fittrackapp.fittrack_mobile.presentation.statistic.DailyStatisticCard
import com.fittrackapp.fittrack_mobile.presentation.statistic.DateList
import com.fittrackapp.fittrack_mobile.ui.theme.DarkRedPink
import com.fittrackapp.fittrack_mobile.ui.theme.RedPink
import com.fittrackapp.fittrack_mobile.ui.theme.gray
import com.fittrackapp.fittrack_mobile.utils.format
import com.fittrackapp.fittrack_mobile.utils.formatToDate
import com.fittrackapp.fittrack_mobile.utils.round
import com.fittrackapp.fittrack_mobile.utils.toFormattedString

@Composable
fun StatisticsScreen(
    viewModel: StatisticsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Statistics",
            style = MaterialTheme.typography.headlineLarge.copy(
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(horizontal = 16.dp),
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                DateList(viewModel)

                Spacer(Modifier.height(20.dp))

                // Selected Date
                if (state.currentSummary != null) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        text = state.currentSummary?.date?.let {
                            it.formatToDate("yyyy-MM-dd")?.format("EEE, dd MMM yyyy")
                        } ?: "No date selected",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.ExtraBold,
                            color = MaterialTheme.colorScheme.onSurface
                        ),
                        textAlign = TextAlign.Center
                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(30.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Box(
                        contentAlignment = Alignment.Center,
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .size(200.dp),
                            progress = {
                                (state.currentSummary?.totalCalories?.toFloat()
                                    ?: 0f) / (state.currentSummary?.targetCalorie?.toFloat() ?: 1f)
                            },
                            color = RedPink,
                            trackColor = DarkRedPink,
                            strokeWidth = 35.dp,
                            gapSize = 10.dp,
                        )

                        Text(
                            text = "${
                                if ((state.currentSummary?.totalCalories ?: 0.0) >= (state.currentSummary?.targetCalorie ?: 0.0)) {
                                    "100"
                                } else {
                                    ((state.currentSummary?.totalCalories?.toFloat()
                                        ?.div(state.currentSummary?.targetCalorie?.toFloat() ?: 1f) ?: 0f) * 100f).toInt()
                                }
                            }%",
                            style = MaterialTheme.typography.headlineMedium.copy(
                                color = MaterialTheme.colorScheme.onSurface,
                                fontWeight = FontWeight.Bold
                            ),
                        )
                    }

                    DailyStatisticCard(
                        viewModel
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Column {
                            Text(
                                text = "Steps",
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            )
                            Text(
                                text = state.currentSummary?.totalSteps?.toFormattedString() ?: "0",
                                style = MaterialTheme.typography.headlineMedium.copy(
                                    color = gray,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                        Column {
                            Text(
                                text = "Distance",
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            )
                            Text(
                                text = "${state.currentSummary?.totalDistance?.toFormattedString() ?: "0"} km",
                                style = MaterialTheme.typography.headlineMedium.copy(
                                    color = gray,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}