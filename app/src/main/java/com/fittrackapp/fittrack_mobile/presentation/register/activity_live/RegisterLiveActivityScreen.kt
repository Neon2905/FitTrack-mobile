package com.fittrackapp.fittrack_mobile.presentation.register

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fittrackapp.fittrack_mobile.presentation.register.activity_live.MapSection
import com.fittrackapp.fittrack_mobile.presentation.register.activity_live.BottomSection
import com.fittrackapp.fittrack_mobile.presentation.register.activity_live.sections.ResultSheet
import com.fittrackapp.fittrack_mobile.sheet.BottomSheetController

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun RegisterLiveActivityScreen(viewModel: RegisterLiveActivityViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    var showDialog: Boolean by remember { mutableStateOf(false) }
    fun setShowDialog(value: Boolean) {
        showDialog = value
    }

    fun onResultDismiss() {
        setShowDialog(true)
    }

    LaunchedEffect(state.showResultModal) {
        if (state.showResultModal) {
            BottomSheetController.show(
                onDismissRequest = { onResultDismiss() }
            ) {
                ResultSheet(
                    viewModel = viewModel,
                    onDismiss = { onResultDismiss() }
                )
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = {
                setShowDialog(false)
                viewModel.showResultModal()
            },
            dismissButton = {
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    onClick = {
                        setShowDialog(false)
                        viewModel.showResultModal()
                    },
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = MaterialTheme.colorScheme.onSurface,
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                ) {
                    Text("Discard")
                }
            },
            confirmButton = {
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    onClick = {
                        setShowDialog(false)
                        BottomSheetController.hide()
                        viewModel.reset()
                    },
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = MaterialTheme.colorScheme.onSurface,
                        containerColor = MaterialTheme.colorScheme.error,
                    ),
                ) {
                    Text("Confirm")
                }
            },
            title = {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "CONFIRM",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    ),
                    textAlign = TextAlign.Center
                )
            },
            text = {
                Text(
                    text = "Are you sure you want to stop the activity? You will lose all the data collected.",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onSurface
                    )
                )
            }
        )
    }
    Box {
        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.Bottom,
        ) {
            MapSection(
                viewModel = viewModel,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .offset(y = 10.dp)
            )

            BottomSection(
                viewModel
            )
        }
    }
}