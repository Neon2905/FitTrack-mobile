package com.fittrackapp.fittrack_mobile.presentation

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fittrackapp.fittrack_mobile.data.local.entity.ActivityEntity
import com.fittrackapp.fittrack_mobile.data.local.prefs.PrefKeys
import com.fittrackapp.fittrack_mobile.sheet.BottomSheetController
import com.fittrackapp.fittrack_mobile.ui.theme.BluePrimary
import com.fittrackapp.fittrack_mobile.utils.toValueString
import kotlin.time.Duration.Companion.seconds
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.core.chart.line.LineChart
import com.patrykandpatrick.vico.core.component.shape.LineComponent
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import com.patrykandpatrick.vico.core.entry.FloatEntry
import kotlin.math.absoluteValue
import kotlin.math.roundToInt


@Composable
fun ExperimentScreen(viewmodel: SummaryViewModel = hiltViewModel()) {
    val state by viewmodel.state.collectAsStateWithLifecycle()
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(WindowInsets.systemBars.asPaddingValues()),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        item {
            val options = listOf(
                MenuItem(
                    title = "D",
                    value = "TODAY",
                ),
                MenuItem(
                    title = "W",
                    value = "THIS_WEEK",
                ),
                MenuItem(
                    title = "M",
                    value = "THIS_MONTH",
                ),
                MenuItem(
                    title = "Y",
                    value = "THIS_YEAR",
                ),
            )
            var selectedItem: MenuItem by remember {
                mutableStateOf(options.firstOrNull {
                    it.value == state.selectedPeriod.toValueString()
                }
                    ?: options.first())
            }

            Selector(
                options = options,
                selectedOption = selectedItem,
                onOptionSelected = { item ->
                    selectedItem = item
                    viewmodel.onSelectedPeriodChanged(item)
                },
                modifier = Modifier.fillMaxWidth()
            )

//            Chart(
//                chart = lineChart(),
//                chartModelProducer = ChartEntryModelProducer(
//                    listOf(
//                        state.filteredActivities.mapIndexed { index, activities ->
//                            FloatEntry(
//                                index.toFloat()+1,
//                                activities.sumOf { activity -> activity.steps }.toFloat()
//                            )
//                        }
//                    )
//                ),
//                // Remove left axis labels by passing null
//                startAxis = null,
//                // Offset X axis so 0 is not flush with Y axis
//                bottomAxis = rememberBottomAxis(
//                    guideline = null,
//                    labelRotationDegrees = 0f,
//                    tickLength = 0.dp,
//                ),
//                modifier = Modifier
//                    .fillMaxWidth()
//            )
            AdaptiveLineChart(
                data =
                    state.filteredActivities.mapIndexed { index, activities ->
                        Log.i("ExperimentScreen", "Index: $index, Activities: $activities")
                        Log.i(
                            "ExperimentScreen",
                            "Steps: ${activities.sumOf { activity -> activity.steps }}"
                        )
                        FloatEntry(
                            index.toFloat() + 1,
                            activities.sumOf { activity -> activity.steps }.toFloat()
                        )
                    }
            )


            Button(onClick = {
                BottomSheetController.show {
                    Text("This is a global bottom sheet!")
                }
            }) {
                Text("Open Sheet")
            }

            Button(onClick = {
                PrefKeys.dailyCalorieGoal += 50

            }) {
                Text("Increase Daily Calorie Goal")
            }

            val goal by PrefKeys.dailyCalorieGoalFlow.collectAsState(initial = PrefKeys.dailyCalorieGoal)

            Text(
                text = "Daily Calorie Goal: $goal",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Composable
fun ActivityEntityItem(activity: ActivityEntity) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = MaterialTheme.shapes.medium,
        tonalElevation = 2.dp
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = activity.id.toString(), style = MaterialTheme.typography.titleMedium)
            Text(text = "Type: ${activity.type}", style = MaterialTheme.typography.bodyMedium)
            Text(
                text = "Distance: ${activity.distance} km",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Calories: ${activity.caloriesBurned} kcal",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Steps: ${activity.steps}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Start Time: ${activity.startTime}",
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                // TODO: Format the duration properly
                text = "Duration: ${activity.duration.seconds.inWholeMinutes} min",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}


@Composable
fun AdaptiveLineChart(
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
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            chart = lineChart(
                lines = listOf(
                    LineChart.LineSpec(
                        lineColor = lineColor.toArgb(),
                        lineThicknessDp = 2f,
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