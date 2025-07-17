package com.fittrackapp.fittrack_mobile.presentation.statistic.sections

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fittrackapp.fittrack_mobile.data.local.prefs.PrefKeys
import com.fittrackapp.fittrack_mobile.sheet.BottomSheetController
import com.fittrackapp.fittrack_mobile.ui.theme.RedPink
import com.fittrackapp.fittrack_mobile.ui.theme.green
import com.fittrackapp.fittrack_mobile.utils.round
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun ChangeDailyGoalSheet() {

    val dailyCalorieGoal =
        remember { mutableIntStateOf(PrefKeys.dailyCalorieGoal.toInt()) }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .height(IntrinsicSize.Min),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val delta = 10
        Text(
            text = "Daily Calorie Goal",
            style = MaterialTheme.typography.titleLarge.copy(
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold
            ),
        )
        Text(
            text = "Set a goal based on how active you are,\n" +
                    "or how active you'd like to be, each day.",
            style = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            ),
            modifier = Modifier.padding(top = 8.dp, bottom = 16.dp)
        )
        Row(
            modifier = Modifier
                .height(150.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ElevatedButton(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(50))
                    .shadow(4.dp, RoundedCornerShape(50)),
                onClick = {
                    dailyCalorieGoal.value -= delta
                },
                content = {
                    Text(
                        "-",
                        fontSize = 25.sp
                    )
                },
                colors = ButtonDefaults.elevatedButtonColors(
                    containerColor = RedPink
                ),
                contentPadding = PaddingValues(0.dp) // Required for icon-only buttons
            )
            Column(
                modifier = Modifier,
                horizontalAlignment = Alignment.CenterHorizontally

            ) {
                Text(
                    text = dailyCalorieGoal.intValue.toString(),
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    ),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
                Text(
                    text = "CALORIES/Day",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    ),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            }

            ElevatedButton(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(50))
                    .shadow(4.dp, RoundedCornerShape(50)),
                onClick = {
                    dailyCalorieGoal.intValue += delta
                    Log.i("ChangeDailyGoalSheet", "New daily calorie goal: $dailyCalorieGoal")
                },
                content = {
                    Icon(
                        modifier = Modifier
                            .size(20.dp),
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add",
                    )
                },
                colors = ButtonDefaults.elevatedButtonColors(
                    containerColor = RedPink
                ),
                contentPadding = PaddingValues(0.dp) // Required for icon-only buttons
            )
        }
        ExtendedFloatingActionButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            onClick = {
                PrefKeys.dailyCalorieGoal = dailyCalorieGoal.intValue.toDouble()
                BottomSheetController.hide()
            },
            containerColor = MaterialTheme.colorScheme.surface
        ) {
            Text(
                text = "Change Daily Goal",
                color = green,
            )
        }
    }
}