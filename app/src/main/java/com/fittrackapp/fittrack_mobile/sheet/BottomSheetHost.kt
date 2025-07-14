package com.fittrackapp.fittrack_mobile.sheet

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.fittrackapp.fittrack_mobile.presentation.BottomSheetModal
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun BottomSheetHost(content: @Composable () -> Unit) {
    val coroutineScope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var sheetContent by remember { mutableStateOf<(@Composable () -> Unit)?>(null) }

    // Init controller
    LaunchedEffect(Unit) {
        BottomSheetController.init { newContent ->
            coroutineScope.launch {
                if (newContent == null) {
                    sheetState.hide()
                    sheetContent = null
                } else {
                    sheetContent = newContent
                    sheetState.show()
                }
            }
        }
    }

    Box {
        content()

        sheetContent?.let {
            BottomSheetModal(
                sheetState = sheetState,
                onDismiss = { BottomSheetController.hide() },
            ) { it() }
        }
    }
}
