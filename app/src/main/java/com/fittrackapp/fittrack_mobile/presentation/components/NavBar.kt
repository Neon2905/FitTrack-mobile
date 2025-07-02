package com.fittrackapp.fittrack_mobile.presentation

import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Assignment
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.fittrackapp.fittrack_mobile.navigation.NavRoute
import com.fittrackapp.fittrack_mobile.navigation.Navigator

data class NavItem(
    val route: String,
    val icon: ImageVector,
    val label: String
)

val navItems = listOf(
    NavItem(NavRoute.Dashboard.route, Icons.Default.Home, "Home"),
    NavItem(NavRoute.Register.LiveActivity.route, Icons.AutoMirrored.Filled.Assignment, "Activity"),
    NavItem(NavRoute.ImportedScreen.route, Icons.Default.InsertChart, "Statistics"),
    NavItem(NavRoute.Settings.route, Icons.Default.Settings, "Settings")
)

@Composable
fun NavBar(
    modifier: Modifier = Modifier
) {
    val currentRoute by Navigator.currentRoute.collectAsState()

    if (currentRoute != NavRoute.Auth.route) {

        NavigationBar(modifier = modifier) {
            navItems.forEach { item ->
                NavigationBarItem(
                    selected = currentRoute == item.route,
                    onClick = {
                        Navigator.navigate(item.route);
                    },
                    icon = {
                        Icon(imageVector = item.icon, contentDescription = item.label)
                    },
                    label = { Text(text = item.label) },
                    alwaysShowLabel = false
                )
            }
        }

    }
}
