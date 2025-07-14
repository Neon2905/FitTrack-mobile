package com.fittrackapp.fittrack_mobile.sheet

import androidx.compose.runtime.Composable

object BottomSheetController {
    private var _showSheet: ((@Composable () -> Unit)?) -> Unit = {}

    fun init(showSheet: ((@Composable () -> Unit)?) -> Unit) {
        _showSheet = showSheet
    }

    fun show(content: @Composable () -> Unit) {
        _showSheet(content)
    }

    fun hide() {
        _showSheet(null)
    }
}