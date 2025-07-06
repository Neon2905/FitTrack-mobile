package com.fittrackapp.fittrack_mobile.presentation.register.activity_live.sections

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.DirectionsWalk
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.sharp.PinDrop
import androidx.compose.material.icons.sharp.Timer
import androidx.compose.material.icons.sharp.Whatshot
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.fittrackapp.fittrack_mobile.presentation.DropdownMenu
import com.fittrackapp.fittrack_mobile.presentation.MenuItem
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fittrackapp.fittrack_mobile.presentation.register.RegisterLiveActivityViewModel
import kotlin.math.roundToInt

@Composable
fun TargetSection(
    viewModel: RegisterLiveActivityViewModel
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val typeMenuItems = listOf(
        MenuItem(
            title = "Distance",
            value = "distance",
            icon = Icons.Sharp.PinDrop,
        ),
        MenuItem(
            title = "Duration",
            value = "duration",
            icon = Icons.Sharp.Timer,
        ),
        MenuItem(
            title = "Calories",
            value = "calorie",
            icon = Icons.Sharp.Whatshot,
        ),
        MenuItem(
            title = "Steps",
            value = "steps",
            icon = Icons.AutoMirrored.Rounded.DirectionsWalk
        )
    )

    var selectedItem by remember {
        mutableStateOf(typeMenuItems.firstOrNull { it.value == state.targetType }
            ?: typeMenuItems.first())
    }

    // Target value with unit
    val targetValueWithUnit: String =
        when (state.targetType) {
            "distance" -> "${state.targetValue/1000} km"
            "duration" -> "${state.targetValue.toInt()} min"
            "calorie" -> "${state.targetValue.toInt()}" // value too large to show with unit
            "steps" -> "${state.targetValue.toInt()}" // value too large to show with unit
            else -> " units"
        }

    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier = Modifier.width(IntrinsicSize.Min),
            horizontalAlignment = CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text(
                text = "Your goal",
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = Bold)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                ElevatedButton(
                    onClick = viewModel::decreaseTargetValue,
                    modifier = Modifier
                        .width(48.dp)
                        .height(48.dp)
                        .clip(RoundedCornerShape(50))
                        .shadow(4.dp, RoundedCornerShape(50)),
                    colors = ButtonColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        disabledContainerColor = MaterialTheme.colorScheme.primaryContainer.copy(
                            alpha = 0.5f
                        ),
                        disabledContentColor = MaterialTheme.colorScheme.onPrimaryContainer.copy(
                            alpha = 0.5f
                        )
                    )
                ) {
                    Text("-")
                }
                Text(
                    modifier = Modifier.width(100.dp),
                    textAlign = TextAlign.Center,
                    text = targetValueWithUnit,
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = Bold)
                )

                ElevatedButton(
                    onClick = viewModel::increaseTargetValue,
                    modifier = Modifier
                        .size(48.dp) // cleaner than setting width + height separately
                        .clip(RoundedCornerShape(50))
                        .shadow(4.dp, RoundedCornerShape(50)),
                    colors = ButtonDefaults.elevatedButtonColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        disabledContainerColor = MaterialTheme.colorScheme.primaryContainer.copy(
                            alpha = 0.5f
                        ),
                        disabledContentColor = MaterialTheme.colorScheme.onPrimaryContainer.copy(
                            alpha = 0.5f
                        )
                    ),
                    contentPadding = PaddingValues(0.dp) // important for icon-only buttons
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Increase Target",
                        tint = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier
                            .size(20.dp)
                            .then(Modifier.background(Color.Transparent)),
                    )
                }

            }
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = CenterHorizontally
            ) {
                var showMenu by remember { mutableStateOf(false) };
                Row(
                    modifier = Modifier
                        .width(IntrinsicSize.Min)
                        .clickable { showMenu = true },
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    selectedItem?.icon?.let { icon ->
                        Icon(
                            imageVector = icon,
                            contentDescription = "Selected Type",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    selectedItem?.title?.let { Text(it) }
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = "Arrow Down",
                        tint = MaterialTheme.colorScheme.primary,
                    )
                }
                DropdownMenu(
                    expanded = showMenu,
                    onDismissRequest = { showMenu = false },
                    menuItems = typeMenuItems,
                    onItemSelected = { item ->
                        selectedItem = item
                        viewModel.onTargetTypeChange(item.value)
                    },
                    selectedItem = selectedItem
                )
            }
        }
    }
}