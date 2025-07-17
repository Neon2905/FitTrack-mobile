package com.fittrackapp.fittrack_mobile.presentation.register.activity_live.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.DirectionsRun
import androidx.compose.material.icons.automirrored.filled.DirectionsWalk
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.PinDrop
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fittrackapp.fittrack_mobile.presentation.register.RegisterLiveActivityViewModel
import com.fittrackapp.fittrack_mobile.sheet.BottomSheetController
import com.fittrackapp.fittrack_mobile.utils.asString
import com.fittrackapp.fittrack_mobile.utils.formatString
import com.fittrackapp.fittrack_mobile.utils.round
import com.fittrackapp.fittrack_mobile.utils.toFormattedString
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@Composable
fun ResultSheet(
    viewModel: RegisterLiveActivityViewModel,
    onDismiss: () -> Unit = {}
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val progress = viewModel.getCurrentValue() / state.targetValue

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Activity Result",
            style = MaterialTheme.typography.headlineLarge.copy(
                fontWeight = FontWeight.ExtraBold,
            ),
            modifier = Modifier
                .fillMaxWidth(),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )

        if (progress >= 0.1f && !state.isTargetReached) {
            Text(
                text = "There's some record left.\nDo you want to save them before stopping?",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .fillMaxWidth(),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.LocalFireDepartment,
                contentDescription = "Activity Icon",
                modifier = Modifier
                    .padding(end = 8.dp)
                    .size(80.dp),
                tint = Color(0xFFDA3131)
            )
            Text(
                text = "${state.calories.toInt()} kcal",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                ),
                modifier = Modifier.padding(start = 8.dp)
            )
            Text(
                text = "burned from ${state.activityType.asString()}",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.SemiBold
                ),
                modifier = Modifier.padding(start = 8.dp, top = 4.dp),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp)
                    .padding(horizontal = 30.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = CenterVertically
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.DirectionsWalk,
                        contentDescription = "Steps"
                    )
                    Text(
                        text = "${state.steps} steps",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.AccessTime,
                        contentDescription = "Duration"
                    )
                    val hours = state.duration.inWholeHours
                    val minutes = state.duration.inWholeMinutes % 60
                    Text(
                        text =
                            (if (hours > 0) "$hours h " else "") + "$minutes min",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.PinDrop,
                        contentDescription = "Distance"
                    )
                    Text(
                        text = "${(state.distance.round(2).toDouble()/1000).toFormattedString()} km",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }
            }
            val coroutineScope = rememberCoroutineScope()
            FloatingActionButton(
                onClick = {
                    coroutineScope.launch {
                        viewModel.save()
                        BottomSheetController.hide()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                containerColor = Color(0xFF31E557),
            )
            {
                Text(
                    text =
                        if (state.isSaving)
                            "Saving..."
                        else
                            "Save Activity",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                    )
                )
            }
            TextButton(
                onClick = {
                    onDismiss()
                }
            ) {
                Text("Dismiss")
            }
        }
    }
}
