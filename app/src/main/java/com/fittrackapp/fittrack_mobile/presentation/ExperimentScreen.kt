package com.fittrackapp.fittrack_mobile.presentation

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.SolidColor
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fittrackapp.fittrack_mobile.presentation.register.RegisterLiveActivityViewModel
import com.fittrackapp.fittrack_mobile.presentation.register.activity_live.LiveSection
import com.fittrackapp.fittrack_mobile.presentation.register.activity_live.sections.ActionSection
import com.fittrackapp.fittrack_mobile.presentation.register.activity_live.sections.TargetSection

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExperimentScreen(viewmodel: RegisterLiveActivityViewModel = hiltViewModel()) {
    val state by viewmodel.state.collectAsStateWithLifecycle()
    Column(
        Modifier.padding(WindowInsets.systemBars.asPaddingValues())
    ) {

        LaunchedEffect(Unit) {
            viewmodel.fetchInitialLocation()
        }

        // Example of a button to show a bottom sheet
        Button(onClick = {}) {
            Text("Show Bottom Sheet")
        }

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .offset(y = (-10).dp)
                .weight(1f),
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
                        viewmodel
                    )
                else
                    TargetSection(
                        viewmodel
                    )

                ActionSection(viewmodel)
            }

            if (state.errorMessage != null) {
                Text("Error: ${state.errorMessage}", color = MaterialTheme.colorScheme.error)
            }
            Spacer(modifier = Modifier.height(16.dp))

        }

    }
}