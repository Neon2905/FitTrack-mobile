package com.fittrackapp.fittrack_mobile.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Assignment
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.fittrackapp.fittrack_mobile.navigation.NavRoute
import com.fittrackapp.fittrack_mobile.navigation.Navigator

data class NavItem(
    val route: String,
    val icon: ImageVector,
    val label: String
)

val navItems = listOf(
    NavItem(NavRoute.Dashboard.route, Icons.Default.Home, "Home"),
    NavItem(NavRoute.Statistics.route, Icons.Default.InsertChart, "Statistics"),
    NavItem(NavRoute.Summary.route, Icons.AutoMirrored.Filled.Assignment, "Summary"),
    NavItem(NavRoute.Settings.route, Icons.Default.Settings, "Settings")
)

@Composable
fun NavBar(
    modifier: Modifier = Modifier
) {
    val currentRoute by Navigator.currentRoute.collectAsState()

    val canShow = currentRoute != NavRoute.Auth.route

    if (!canShow) {
        // Return an empty box to avoid layout issues
        return Box(
            modifier = modifier
                .height(0.dp)
        )
    }

    NavigationBar(
        modifier = modifier
            .height(IntrinsicSize.Min)
            .zIndex(1f)
    ) {
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
