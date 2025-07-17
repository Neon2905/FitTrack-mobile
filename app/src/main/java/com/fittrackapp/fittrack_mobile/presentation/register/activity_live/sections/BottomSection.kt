package com.fittrackapp.fittrack_mobile.presentation.register.activity_live

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fittrackapp.fittrack_mobile.presentation.register.RegisterLiveActivityViewModel

@Composable
fun BottomSection(
    viewModel: RegisterLiveActivityViewModel
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
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
    }
}