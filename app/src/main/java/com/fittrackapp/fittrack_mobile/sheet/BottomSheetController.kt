package com.fittrackapp.fittrack_mobile.sheet

import androidx.compose.runtime.Composable

object BottomSheetController {
    private var _showSheet: ((@Composable () -> Unit)?, () -> Unit) -> Unit = { _, _ -> }

    fun init(showSheet: ((@Composable () -> Unit)?, () -> Unit) -> Unit) {
        _showSheet = showSheet
    }

    fun show(
        onDismissRequest: () -> Unit = { },
        content: @Composable () -> Unit
    ) {
        _showSheet(
            {
                content()
            },
            {
                hide()
                onDismissRequest()
            }
        )

    }

    fun hide() {
        _showSheet(null) {

        }
    }
}