package com.fittrackapp.fittrack_mobile.presentation.register.activity_live

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.fittrackapp.fittrack_mobile.navigation.Navigator
import com.fittrackapp.fittrack_mobile.ui.theme.darkBlue

@Composable
fun NavigationSection(
    modifier: Modifier = Modifier
) {

    TextButton(
        onClick = { Navigator.goBack() },
        modifier = modifier
            .height(40.dp)
            .absoluteOffset(x = 0.dp, y = 10.dp)
            .zIndex(1f)
    ) {
        Text(
            text = "< Back",
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.ExtraBold,
            ),
            color = darkBlue,
        )
    }
}