package com.fittrackapp.fittrack_mobile.presentation.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import com.fittrackapp.fittrack_mobile.presentation.dashboard.DateList
import com.fittrackapp.fittrack_mobile.presentation.dashboard.Header
import java.time.ZoneId

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun DashboardScreen(vm: DashboardViewModel = viewModel()) {

    val activities = vm.activities
    val currentActivity = vm.currentActivity

    var currentDate =
        currentActivity.value.date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(WindowInsets.systemBars.asPaddingValues())
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.spacedBy(25.dp)
    ) {
        Header(currentDate)

        DateList(activities.value, currentActivity.value, onSelect = vm::onActivitySelected)

        Box(
            modifier = Modifier,
        ) {
            Card(
                modifier = Modifier
                    .height(160.dp)
                    .fillMaxSize()
                )
            {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Column(
                        modifier = Modifier
                            .padding(start = 16.dp, top = 16.dp)
                    ) {
                        Text(
                            text = "Activity Ring",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                            )
                        )
                    }

                    Text(
                        modifier = Modifier
                            .padding(end = 16.dp)
                            .align(Alignment.CenterVertically),
                        text = buildAnnotatedString {
                            withStyle(
                                style = MaterialTheme.typography.labelLarge.toSpanStyle()
                                    .copy(fontWeight = FontWeight.ExtraBold)
                            ) {
                                append("Progress\n")
                            }
                            withStyle(
                                style = MaterialTheme.typography.labelLarge.toSpanStyle().copy(
                                    color = MaterialTheme.colorScheme.primary,
                                    fontWeight = FontWeight.ExtraBold
                                )
                            ) {
                                append("${currentActivity.value.calories}/500 CAL")
                            }
                        },
                    )
                }
            }
        }


    }
}

