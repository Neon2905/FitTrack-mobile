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
import com.fittrackapp.fittrack_mobile.presentation.register.activity_live.ActionSection
import com.fittrackapp.fittrack_mobile.presentation.register.activity_live.BottomSheetSection
import com.fittrackapp.fittrack_mobile.presentation.register.activity_live.NavigationSection
import com.fittrackapp.fittrack_mobile.presentation.register.activity_live.TargetSection
import com.fittrackapp.fittrack_mobile.ui.theme.darkBlue

@Preview
@Composable
fun RegisterLiveActivityScreen(viewModel: RegisterLiveActivityViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchInitialLocation()
    }
    Box {

        NavigationSection()

        Column(
            verticalArrangement = Arrangement.SpaceBetween,
        ) {

            // TODO: Fix map error
            MapSection(
                tracks = state.tracks, currentLocation = state.currentLocation,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .offset(y = 10.dp)
            )

            BottomSheetSection(
                viewModel
            )
        }
    }
}