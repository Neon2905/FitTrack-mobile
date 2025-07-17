package com.fittrackapp.fittrack_mobile.presentation.statistic

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fittrackapp.fittrack_mobile.data.local.entity.DailySummaryEntity
import com.fittrackapp.fittrack_mobile.domain.model.Activity
import com.fittrackapp.fittrack_mobile.presentation.StatisticsViewModel
import com.fittrackapp.fittrack_mobile.utils.toDateOrNull
import com.fittrackapp.fittrack_mobile.utils.tryCastToLocalDateTime

@Composable
fun DateList(
    viewModel: StatisticsViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsStateWithLifecycle()
    val summaries: List<DailySummaryEntity> =
        listOfNotNull(state.todaySummary) + state.dailySummaries

    LazyRow(
        modifier = Modifier
            .wrapContentSize(),
    ) {
        items(summaries) { summary ->
            DateCard(
                summary,
                state.currentSummary ?: summaries.firstOrNull(),
                viewModel::onActivitySelected
            )
        }
    }
}

@Composable
fun DateCard(
    summary: DailySummaryEntity,
    currentSummary: DailySummaryEntity?,
    onSelect: (DailySummaryEntity) -> Unit = {}
) {
    val date =
        summary.date.toDateOrNull()

    Card(
        modifier = Modifier
            .padding(8.dp)
            .wrapContentSize()
            .clickable { onSelect(summary) },
        colors = CardDefaults.cardColors(
            containerColor = if (currentSummary == summary) {
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
                text = date?.let {
                    val localDate = it.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
                    DateTimeFormatter.ofPattern("dd").format(localDate)
                } ?: "",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            )
            Text(
                text = date?.let {
                    val localDate = it.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
                    DateTimeFormatter.ofPattern("E").format(localDate)
                } ?: "",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            )
        }
    }
}
