package com.fittrackapp.fittrack_mobile.presentation.register

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import com.fittrackapp.fittrack_mobile.navigation.Navigator
import com.fittrackapp.fittrack_mobile.presentation.register.activity_live.LiveSection
import com.fittrackapp.fittrack_mobile.presentation.register.activity_live.MapSection
import com.fittrackapp.fittrack_mobile.presentation.register.activity_live.sections.ActionSection
import com.fittrackapp.fittrack_mobile.presentation.register.activity_live.sections.TargetSection
import com.fittrackapp.fittrack_mobile.ui.theme.darkBlue

@Preview
@Composable
fun RegisterLiveActivityScreen(viewModel: RegisterLiveActivityViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchInitialLocation()
    }
    TextButton(
        onClick = { Navigator.goBack() },
        modifier = Modifier
            .padding(
                start = 8.dp,
                top = WindowInsets.systemBars.asPaddingValues().calculateTopPadding()
            )
            .height(40.dp)
            .absoluteOffset(x = 0.dp, y = 10.dp)
            .zIndex(1f)
    ) {
        Text(
            text = "< Back",
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.ExtraBold,
            ),
            color = darkBlue,
        )
    }

    Column(
        Modifier
            .padding(WindowInsets.systemBars.asPaddingValues()),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {

        // TODO: Fix map error
        MapSection(
            tracks = state.tracks, currentLocation = state.currentLocation,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .offset(y = (-10).dp),
            shape = MaterialTheme.shapes.large.copy(
                bottomEnd = CornerSize(0.dp),
                bottomStart = CornerSize(0.dp)
            ),
            border = ButtonDefaults.outlinedButtonBorder(true).copy(
                brush = SolidColor(MaterialTheme.colorScheme.outlineVariant)
            ),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .animateContentSize()
                    .padding(top = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                if (state.isLive)
                    LiveSection(
                        viewModel
                    )
                else
                    TargetSection(
                        viewModel
                    )

                ActionSection(viewModel)
                Spacer(modifier = Modifier.height(16.dp))
            }

            if (state.errorMessage != null) {
                Text("Error: ${state.errorMessage}", color = MaterialTheme.colorScheme.error)
            }
            Spacer(modifier = Modifier.height(16.dp))

        }

        // Space for navigation bar
        Spacer(modifier = Modifier.height(50.dp))
    }
}