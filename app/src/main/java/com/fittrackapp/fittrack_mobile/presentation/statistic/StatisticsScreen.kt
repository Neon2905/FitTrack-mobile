package com.fittrackapp.fittrack_mobile.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fittrackapp.fittrack_mobile.presentation.statistic.DateList

@Composable
fun StatisticsScreen(
    viewModel: StatisticsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .padding(WindowInsets.systemBars.asPaddingValues())
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Statistics",
            style = MaterialTheme.typography.headlineLarge.copy(
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(horizontal = 16.dp),
        )

        DateList(viewModel)

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {
            item {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(30.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(260.dp),
                        horizontalArrangement = Arrangement.spacedBy(30.dp)
                    ) {
                        Card(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight(),
                            shape = RoundedCornerShape(16.dp),
                            elevation = elevatedCardElevation(4.dp),
                        ) {
                            Text("Something")
                        }
                        Card(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight(),
                            shape = RoundedCornerShape(16.dp),
                            elevation = elevatedCardElevation(4.dp),
                        ) {
                            Text("Something")
                        }
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(260.dp),
                        horizontalArrangement = Arrangement.spacedBy(30.dp)
                    ) {
                        Card(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight(),
                            shape = RoundedCornerShape(16.dp),
                            elevation = elevatedCardElevation(4.dp),
                        ) {
                            Text("Something")
                        }
                        Card(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight(),
                            shape = RoundedCornerShape(16.dp),
                            elevation = elevatedCardElevation(4.dp),
                        ) {
                            Text("Something")
                        }
                    }
                }
            }
        }
    }
}