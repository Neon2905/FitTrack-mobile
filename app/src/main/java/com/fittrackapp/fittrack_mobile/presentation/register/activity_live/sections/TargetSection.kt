package com.fittrackapp.fittrack_mobile.presentation.register.activity_live.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.layout
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min

@Composable
fun TargetSection(
    target: String?,
    onTypeChange: (String) -> Unit,
    onTargetDecrease: () -> Unit,
    onTargetIncrease: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center // center content
    ) {
        Column(
            modifier = Modifier.width(IntrinsicSize.Min),
            horizontalAlignment = CenterHorizontally
        ) {
            Text(
                text = "Your goal",
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = Bold)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = onTargetDecrease, modifier = Modifier
                        .clip(RoundedCornerShape(50))
                ) {
                    Text("-")
                }
                Text(
                    modifier = Modifier.width(100.dp),
                    textAlign = TextAlign.Center,
                    text = target ?: "0",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = Bold)
                )
                Button(
                    onClick = onTargetIncrease, modifier = Modifier
                        .clip(RoundedCornerShape(50))
                ) {
                    Text("+")
                }
            }
            Row(
                modifier = Modifier.width(IntrinsicSize.Min),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Distance")
                Icon(
                    imageVector = Icons.Default.ArrowDownward,
                    contentDescription = "Arrow Down",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}