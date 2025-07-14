package com.fittrackapp.fittrack_mobile.presentation

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fittrackapp.fittrack_mobile.data.local.entity.ActivityEntity
import com.fittrackapp.fittrack_mobile.utils.toValueString
import kotlin.time.Duration.Companion.seconds
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.rememberStartAxis
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import com.patrykandpatrick.vico.core.entry.FloatEntry


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
                    Log.i(
                        "ExperimentScreen",
                        "Checking item: ${it.value} against ${state.selectedPeriod.toValueString()}"
                    )
                    it.value == state.selectedPeriod.toValueString()
                }
                    ?: options.first())
            }

            Log.i("ExperimentScreen", "Selected item: ${selectedItem}")
            Log.i("ExperimentScreen", "State: ${state.selectedPeriod.toValueString()}")

            Selector(
                options = options,
                selectedOption = selectedItem,
                onOptionSelected = { item ->
                    selectedItem = item
                    viewmodel.onSelectedPeriodChanged(item)
                },
                modifier = Modifier.fillMaxWidth()
            )

            Chart(
                chart = lineChart(),
                chartModelProducer = ChartEntryModelProducer(
                    listOf(
                        state.filteredActivities.mapIndexed { index, activities ->
                            FloatEntry(
                                index.toFloat(),
                                activities.sumOf { activity -> activity.steps }.toFloat()
                            )
                        }
                    )
                ),
                // Remove left axis labels by passing null
                startAxis = null,
                // Offset X axis so 0 is not flush with Y axis
                bottomAxis = rememberBottomAxis(
                    guideline = null,
                    labelRotationDegrees = 0f,
                    tickLength = 0.dp,
                ),
                modifier = Modifier
                    .fillMaxWidth()
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

