package com.fittrackapp.fittrack_mobile.presentation

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.SolidColor
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fittrackapp.fittrack_mobile.data.local.entity.ActivityEntity
import com.fittrackapp.fittrack_mobile.presentation.register.RegisterLiveActivityViewModel
import com.fittrackapp.fittrack_mobile.presentation.register.activity_live.LiveSection
import com.fittrackapp.fittrack_mobile.presentation.register.activity_live.ActionSection
import com.fittrackapp.fittrack_mobile.presentation.register.activity_live.TargetSection
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

@Composable
fun ExperimentScreen(viewmodel: ExperimentViewModel = hiltViewModel()) {
    val state by viewmodel.state.collectAsStateWithLifecycle()
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(WindowInsets.systemBars.asPaddingValues()),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        item {
            ExtendedFloatingActionButton(
                onClick = viewmodel::addActivity,
            ) {
                Text("Add Activity")
            }
            ExtendedFloatingActionButton(
                onClick = {}
            ) {
                Text("Get Activities")
            }
            ExtendedFloatingActionButton(
                onClick = viewmodel::deleteAllActivities
            ) {
                Text("Delete Activities")
            }
            state.activities.orEmpty().forEach { activity ->
                ActivityEntityItem(activity)
            }
            Spacer(Modifier.height(70.dp))
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