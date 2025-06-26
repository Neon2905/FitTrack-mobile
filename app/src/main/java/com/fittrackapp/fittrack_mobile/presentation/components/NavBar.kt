package com.fittrackapp.fittrack_mobile.presentation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Assignment
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.fittrackapp.fittrack_mobile.utils.navigation.NavRoute
import com.fittrackapp.fittrack_mobile.utils.navigation.Navigator

data class NavItem(
    val route: String,
    val icon: ImageVector,
    val label: String
)

val navItems = listOf(
    NavItem(NavRoute.Dashboard.route, Icons.Default.Home, "Home"),
    NavItem(NavRoute.Auth.route, Icons.AutoMirrored.Filled.Assignment, "Activity"),
    NavItem(NavRoute.Settings.route, Icons.Default.InsertChart, "Statistics"),
    NavItem(NavRoute.Settings.route, Icons.Default.Settings, "Settings")
)

@Composable
fun NavBar(
    modifier: Modifier = Modifier
) {
    val currentRoute by Navigator.currentRoute.collectAsState()

    //TODO: Fix the issue with the currentRoute not being recognized
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
