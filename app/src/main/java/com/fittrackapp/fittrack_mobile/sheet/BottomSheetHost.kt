package com.fittrackapp.fittrack_mobile.sheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetValue
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
import com.fittrackapp.fittrack_mobile.ui.theme.BluePrimary
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun BottomSheetHost(content: @Composable () -> Unit) {
    val coroutineScope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
//        confirmValueChange = { it != SheetValue.Hidden }
    )
    var sheetContent by remember { mutableStateOf<(@Composable () -> Unit)?>(null) }

    var onDismissEvent by remember { mutableStateOf({}) }

    // Init controller
    LaunchedEffect(Unit) {
        BottomSheetController.init { newContent, onDismissRequest ->
            coroutineScope.launch {
                if (newContent == null) {
                    sheetState.hide()
                    sheetContent = null
                } else {
                    onDismissEvent = onDismissRequest
                    sheetContent = {
                        newContent()
                    }
                    sheetState.show()
                }
            }
        }
    }

    Box {
        // Main content
        content()

        sheetContent?.let {
            BottomSheetModal(
                sheetState = sheetState,
                onDismissRequest = onDismissEvent,
            ) { it() }
        }
    }
}
