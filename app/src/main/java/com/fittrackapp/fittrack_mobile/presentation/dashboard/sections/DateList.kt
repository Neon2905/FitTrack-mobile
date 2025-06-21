package com.fittrackapp.fittrack_mobile.presentation.dashboard

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CardDefaults
import com.fittrackapp.fittrack_mobile.domain.model.Activity

@Composable
fun DateList(
    activities: List<Activity>,
    currentActivity: Activity,
    onSelect: (Activity) -> Unit = {}
) {
    LazyRow {
        items(activities) { activity ->
            DateCard(activity, currentActivity, onSelect)
        }
    }
}

@Composable
fun DateCard(activity: Activity, currentActivity: Activity, onSelect: (Activity) -> Unit = {}) {
    val localDate = activity.date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()

    Card(
        modifier = Modifier
            .padding(8.dp)
            .wrapContentSize()
            .clickable { onSelect(activity) },
        colors = CardDefaults.cardColors(
            containerColor = if (currentActivity == activity) {
                MaterialTheme.colorScheme.primaryContainer
            } else {
                MaterialTheme.colorScheme.surfaceVariant
            }
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .width(50.dp)
                .wrapContentHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Text(
                text = DateTimeFormatter.ofPattern("dd").format(localDate),
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            )
            Text(
                text = DateTimeFormatter.ofPattern("E").format(localDate),
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            )
        }
    }
}
