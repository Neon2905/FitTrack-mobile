package com.fittrackapp.fittrack_mobile.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun Selector(
    modifier: Modifier = Modifier,
    options: List<MenuItem>,
    selectedOption: MenuItem? = options.firstOrNull(),
    onOptionSelected: (MenuItem) -> Unit,
    backgroundColor: Color = Color(0xFF434344),
    selectedColor: Color = Color(0xFF797373),
    textColor: Color = Color.White.copy(alpha = 0.8f),
    selectedTextColor: Color = Color.White
) {
    Surface(
        color = backgroundColor,
        shape = RoundedCornerShape(16.dp),
        modifier = modifier
            .height(48.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            options.forEachIndexed { index, option ->
                val isSelected = option == selectedOption

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clickable { onOptionSelected(option) }
                        .background(
                            color =
                                if (isSelected)
                                    selectedColor
                                else
                                    Color.Transparent,
                            shape = RoundedCornerShape(12.dp)
                        )
                        .padding(vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = option.title,
                        color =
                            if (isSelected)
                                selectedTextColor
                            else
                                textColor,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.labelMedium
                    )
                }

                // Divider between buttons (except after the last one)
                if (index < options.size - 1) {
                    VerticalDivider(
                        color = Color.Gray.copy(alpha = 0.4f),
                        modifier = Modifier
                            .width(1.dp)
                            .fillMaxHeight(0.5f)
                    )
                }
            }
        }
    }
}