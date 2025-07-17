package com.fittrackapp.fittrack_mobile.presentation.register.log_activity.sections

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.DirectionsBike
import androidx.compose.material.icons.automirrored.filled.DirectionsRun
import androidx.compose.material.icons.automirrored.filled.DirectionsWalk
import androidx.compose.material.icons.automirrored.rounded.DirectionsWalk
import androidx.compose.material.icons.filled.DirectionsBike
import androidx.compose.material.icons.filled.DirectionsRun
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Pool
import androidx.compose.material.icons.sharp.PinDrop
import androidx.compose.material.icons.sharp.Timer
import androidx.compose.material.icons.sharp.Whatshot
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fittrackapp.fittrack_mobile.presentation.DropdownMenu
import com.fittrackapp.fittrack_mobile.presentation.MenuItem
import com.fittrackapp.fittrack_mobile.presentation.register.LogActivityViewModel
import com.fittrackapp.fittrack_mobile.utils.asActivityType

@Composable
fun Header(
    modifier: Modifier = Modifier,
    viewModel: LogActivityViewModel
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val activityMenuItems = listOf(
        MenuItem(
            title = "Running",
            value = "running",
            icon = Icons.AutoMirrored.Filled.DirectionsRun,
        ),
        MenuItem(
            title = "Walking",
            value = "walking",
            icon = Icons.AutoMirrored.Filled.DirectionsWalk,
        ),
        MenuItem(
            title = "Weight Lifting",
            value = "weightlifting",
            icon = Icons.Default.FitnessCenter,
        ),
        MenuItem(
            title = "Cycling",
            value = "cycling",
            icon = Icons.AutoMirrored.Filled.DirectionsBike
        ),
        MenuItem(
            title = "Swimming",
            value = "swimming",
            icon = Icons.Default.Pool
        ),
    )

    var selectedItem by remember {
        mutableStateOf(activityMenuItems.firstOrNull { it.value == state.activityType.name.lowercase() }
            ?: activityMenuItems.first())
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = CenterHorizontally
    ) {
        var showMenu by remember { mutableStateOf(false) };
        Row(
            modifier = Modifier
                .clickable { showMenu = true },
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            selectedItem.icon?.let { icon ->
                Icon(
                    imageVector = icon,
                    contentDescription = "Selected Type",
                    modifier = Modifier.size(24.dp)
                )
            }
            selectedItem.title?.let { Text(it) }
            Icon(
                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = "Arrow Down",
                tint = MaterialTheme.colorScheme.primary,
            )
        }
        DropdownMenu(
            expanded = showMenu,
            onDismissRequest = { showMenu = false },
            menuItems = activityMenuItems,
            onItemSelected = { item ->
                selectedItem = item
                viewModel.onActivityTypeSelected(item.value.asActivityType())
                showMenu = false
            },
            selectedItem = selectedItem
        )
    }
}