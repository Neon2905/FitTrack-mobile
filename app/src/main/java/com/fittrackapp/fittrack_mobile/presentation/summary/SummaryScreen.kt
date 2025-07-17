package com.fittrackapp.fittrack_mobile.presentation.summary

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.elevatedCardElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.font.FontWeight.Companion.ExtraBold
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fittrackapp.fittrack_mobile.presentation.MenuItem
import com.fittrackapp.fittrack_mobile.presentation.SelectedPeriod
import com.fittrackapp.fittrack_mobile.presentation.Selector
import com.fittrackapp.fittrack_mobile.presentation.SummaryViewModel
import com.fittrackapp.fittrack_mobile.ui.theme.BluePrimary
import com.fittrackapp.fittrack_mobile.ui.theme.blue
import com.fittrackapp.fittrack_mobile.ui.theme.brightBlue
import com.fittrackapp.fittrack_mobile.ui.theme.purple
import com.fittrackapp.fittrack_mobile.utils.toFormattedString
import com.fittrackapp.fittrack_mobile.utils.toValueString
import com.patrykandpatrick.vico.core.entry.FloatEntry

@Composable
fun SummaryScreen(
    viewModel: SummaryViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

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

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
    ) {
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {

                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Summary",
                        style = MaterialTheme.typography.headlineLarge.copy(
                            color = MaterialTheme.colorScheme.onSurface,
                            fontWeight = ExtraBold
                        ),
                    )
                    Text(
                        text = when (state.selectedPeriod) {
                            SelectedPeriod.TODAY -> "Today"
                            SelectedPeriod.THIS_WEEK -> "This Week"
                            SelectedPeriod.THIS_MONTH -> "This Month"
                            SelectedPeriod.THIS_YEAR -> "This Year"
                            else -> ""
                        },
                        style = MaterialTheme.typography.titleLarge.copy(
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                            fontWeight = Bold
                        ),
                    )
                }

                Selector(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp),
                    options = options,
                    selectedOption = selectedItem,
                    onOptionSelected = { item ->
                        selectedItem = item
                        viewModel.onSelectedPeriodChanged(item)
                    },
                )
                Card(
                    elevation = elevatedCardElevation(8.dp),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp, vertical = 15.dp),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        Column {
                            Text(
                                text = "Steps",
                                style = MaterialTheme.typography.titleMedium
                                    .copy(
                                        color = MaterialTheme.colorScheme.onSurface,
                                        fontWeight = Bold
                                    ),
                            )
                            Text(
                                text = state.totalSteps.toFormattedString(),
                                style = MaterialTheme.typography.headlineMedium
                                    .copy(
                                        color = purple,
                                        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                                    ),
                            )
                        }
                        SummaryChart(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(150.dp),
                            data =
                                state.filteredActivities.mapIndexed { index, activities ->
                                    FloatEntry(
                                        index.toFloat() + 1,
                                        activities.sumOf { activity -> activity.steps }.toFloat()
                                    )
                                },
                            lineColor = purple
                        )

                    }
                }
                Card(
                    elevation = elevatedCardElevation(8.dp),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp, vertical = 15.dp),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        Column {
                            Text(
                                text = "Distance",
                                style = MaterialTheme.typography.titleMedium
                                    .copy(
                                        color = MaterialTheme.colorScheme.onSurface,
                                        fontWeight = Bold
                                    ),
                            )
                            Text(
                                text = "${state.totalDistance.toFormattedString()}KM",
                                style = MaterialTheme.typography.headlineMedium
                                    .copy(
                                        color = BluePrimary,
                                        fontWeight = Bold
                                    ),
                            )
                        }
                        SummaryChart(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(150.dp),
                            data =
                                state.filteredActivities.mapIndexed { index, activities ->
                                    FloatEntry(
                                        index.toFloat() + 1,
                                        activities.sumOf { activity -> activity.steps }.toFloat()
                                    )
                                }
                        )
                    }
                }
            }
        }
    }
}