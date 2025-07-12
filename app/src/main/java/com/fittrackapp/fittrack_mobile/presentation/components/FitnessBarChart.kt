package com.fittrackapp.fittrack_mobile.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fittrackapp.fittrack_mobile.ui.theme.BluePrimary
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.column.columnChart
import com.patrykandpatrick.vico.core.component.shape.LineComponent
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import com.patrykandpatrick.vico.core.entry.FloatEntry

@Composable
fun FitnessBarChart(
    hourlyData: List<Float> = List(24) { (0..1000).random().toFloat() },
    modifier: Modifier = Modifier,
    barColor: Color = BluePrimary,
) {
    val entries = hourlyData.mapIndexed { index, value ->
        FloatEntry(index.toFloat(), value)
    }
    val modelProducer = remember { ChartEntryModelProducer(entries) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp)
    ) {

        Chart(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            chart = columnChart(
                spacing = 1.5.dp,
                columns = listOf(
                    LineComponent(
                        color = barColor.toArgb(),
                        thicknessDp = 2f // 👈 This sets the bar width
                    )
                ),
            ),
            bottomAxis = rememberBottomAxis(
                guideline = LineComponent(
                    color = MaterialTheme.colorScheme.onSurface.toArgb(),
                    thicknessDp = .2f
                ),
                label = null
            ),
            chartModelProducer = modelProducer,
        )

        // Custom label row (manual horizontal labels)
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            listOf("00", "06", "12", "18").forEach { label ->
                Text(
                    text = label,
                    color = Color.Gray,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .weight(1f)
                )
            }
        }
    }
}