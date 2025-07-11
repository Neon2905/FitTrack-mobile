package com.fittrackapp.fittrack_mobile.presentation.dashboard

import android.widget.Space
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.elevatedCardElevation
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fittrackapp.fittrack_mobile.ui.theme.DarkRedPink
import com.fittrackapp.fittrack_mobile.ui.theme.RedPink

@Composable
fun ActivityRingSection(viewModel: DashboardViewModel = hiltViewModel()) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    Card(
        shape = MaterialTheme.shapes.large,
        elevation = elevatedCardElevation(4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(22.dp),
        ) {

            Column(
                modifier = Modifier
                    .height(IntrinsicSize.Min)
            ) {
                Text(
                    modifier = Modifier
                        .padding(start = 5.dp),
                    text = "Activity Ring",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                    )
                )
                Spacer(modifier = Modifier.height(10.dp))
                HorizontalDivider(modifier = Modifier.weight(1f))
            }

            Row {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(150.dp),
                    progress = {
                        (state.totalCalories.toFloat()) / 500f
                    },
                    color = RedPink,
                    trackColor = DarkRedPink,
                    strokeWidth = 30.dp,
                    gapSize = 10.dp,
                )

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    modifier = Modifier
                        .padding(bottom = 20.dp, end = 40.dp)
                        .align(Alignment.CenterVertically),
                    text = buildAnnotatedString {
                        withStyle(
                            style = MaterialTheme.typography.titleLarge.toSpanStyle()
                                .copy(fontWeight = FontWeight.Bold)
                        ) {
                            append("Progress\n")
                        }
                        withStyle(
                            style = MaterialTheme.typography.titleLarge.toSpanStyle()
                                .copy(
                                    color = RedPink,
                                    fontWeight = FontWeight.Bold
                                )
                        ) {
                            append("${state.totalCalories}/500 CAL")
                        }
                    },
                )

            }
        }
    }
}

