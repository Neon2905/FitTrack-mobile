package com.fittrackapp.fittrack_mobile.presentation.dashboard

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.elevatedCardElevation
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.fittrackapp.fittrack_mobile.presentation.FitnessBarChart
import com.fittrackapp.fittrack_mobile.ui.theme.BluePrimary
import com.fittrackapp.fittrack_mobile.utils.toFormattedString

@Composable
fun DailySummarySection(
    modifier: Modifier = Modifier,
    title: String = "Distance",
    total: Number = 0.0,
    dataColor: Color = BluePrimary,
    unit: String = "",
    hourlyData: List<Float> = List(24) { 0f },
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Card(
            modifier = modifier
                .fillMaxHeight(),
            shape = MaterialTheme.shapes.large,
            elevation = elevatedCardElevation(4.dp),
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    title,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                    )
                )
                Spacer(modifier = Modifier.height(10.dp))

                HorizontalDivider(modifier = Modifier.weight(1f))

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    "${total.toFormattedString()} $unit",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = dataColor
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                FitnessBarChart(
                    hourlyData = hourlyData,
                    modifier = Modifier.fillMaxWidth(),
                    barColor = dataColor
                )
            }
        }
    }
}