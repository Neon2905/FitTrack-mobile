package com.fittrackapp.fittrack_mobile.presentation

import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

data class MenuItem(
    val title: String,
    val icon: ImageVector? = null,
    val value: String
)

@Composable
fun DropdownMenu(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    menuItems: List<MenuItem>,
    onItemSelected: (MenuItem) -> Unit = { _ -> },
    selectedItem: MenuItem? = null
) {
    if (!expanded || menuItems.isEmpty()) {
        return
    }
    DropdownMenu(
        expanded = true,
        onDismissRequest = onDismissRequest,
        modifier = modifier.then(Modifier.width(IntrinsicSize.Min)),
    ) {
        menuItems.forEach { item ->
            DropdownMenuItem(
                text = { Text(item.title) },
                onClick = { onItemSelected(item) },
                leadingIcon = {
                    if (item.icon != null) {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = null
                        )
                    }
                },
                trailingIcon = {
                    if (selectedItem == item) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    }
}