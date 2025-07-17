package com.fittrackapp.fittrack_mobile.presentation.register.activity_live

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fittrackapp.fittrack_mobile.presentation.register.RegisterLiveActivityViewModel
import com.fittrackapp.fittrack_mobile.ui.theme.green
import com.fittrackapp.fittrack_mobile.ui.theme.redOrange

@Composable
fun ActionSection(
    viewModel: RegisterLiveActivityViewModel
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (!state.isLive) {
            Button(
                onClick = viewModel::start,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = green,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Text("Start")
            }
        } else {
            if (state.onPause) {
                Button(
                    onClick = viewModel::onCompleteOrStopped,
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .fillMaxHeight(),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = redOrange,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    Text("Stop")
                }

                Button(
                    onClick = viewModel::start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = green,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    Text("Continue")
                }
            } else {
                Button(
                    onClick = viewModel::pause,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = green,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    Text("Pause | Stop")
                }
            }
        }
    }
}