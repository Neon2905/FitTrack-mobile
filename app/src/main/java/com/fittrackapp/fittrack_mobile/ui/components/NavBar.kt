package com.fittrackapp.fittrack_mobile.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Assignment
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.fittrackapp.fittrack_mobile.navigation.Navigator

data class NavItem(
    val route: String,
    val icon: ImageVector,
    val label: String
)

val navItems = listOf(
    NavItem("Dashboard", Icons.Default.Home, "Home"),
    NavItem("Settings", Icons.AutoMirrored.Filled.Assignment, "Activity"),
    NavItem("Settings", Icons.Default.InsertChart, "Statistics"),
    NavItem("Settings", Icons.Default.Settings, "Settings")
)

@Composable
fun NavBar(
    modifier: Modifier = Modifier
) {
    val currentRoute = Navigator.getCurrentRoute()

    NavigationBar(modifier = modifier) {
        navItems.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = {
                    if (currentRoute != item.route) {
                        Navigator.navigate(item.route);
                    }
                },
                icon = {
                    Icon(imageVector = item.icon, contentDescription = item.label)
                },
                label = {
                    Text(text = item.label)
                }
            )
        }
    }
}
