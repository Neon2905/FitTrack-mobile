package com.fittrackapp.fittrack_mobile.presentation.summary

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.core.chart.line.LineChart
import com.patrykandpatrick.vico.core.component.shape.LineComponent
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import com.patrykandpatrick.vico.core.entry.FloatEntry
import kotlin.math.roundToInt

@Composable
fun SummaryChart(
    data: List<FloatEntry>,
    modifier: Modifier = Modifier,
    lineColor: Color = BluePrimary,
) {
    val modelProducer = remember(data) { ChartEntryModelProducer(data) }

    val labelIndices = remember(data) {
        if (data.isEmpty()) emptyList()
        else if (data.size == 1) listOf(0)
        else {
            val step = (data.lastIndex / 3.0).roundToInt().coerceAtLeast(1)
            List(4) { i -> (i * step).coerceAtMost(data.lastIndex) }
        }
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(160.dp)
    ) {
        Chart(
            modifier = modifier
                .fillMaxWidth()
                .weight(1f),
            chart = lineChart(
                lines = listOf(
                    LineChart.LineSpec(
                        lineColor = lineColor.toArgb(),
                        lineThicknessDp = 4f,
                    )
                )
            ),
            bottomAxis = rememberBottomAxis(
                guideline = LineComponent(
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f).toArgb(),
                    thicknessDp = 0.5f
                ),
                label = null
            ),
            chartModelProducer = modelProducer,
        )

        // Label row with 4 evenly spaced labels
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            labelIndices.forEach { index ->
                Text(
                    text = index.toString(),
                    color = Color.Gray,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}