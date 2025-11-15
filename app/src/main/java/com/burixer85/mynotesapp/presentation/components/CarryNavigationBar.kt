package com.burixer85.mynotesapp.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.burixer85.mynotesapp.presentation.navigation.CategoriesRoute
import com.burixer85.mynotesapp.presentation.navigation.QuickNotesRoute

private data class BottomBarItem(
    val route: Any,
    val label: String,
    val icon: ImageVector
)

@Composable
fun CarryNavigationBar(
    navController: NavController,
    onItemClick: (route: Any) -> Unit
) {
    val bottomBarItems = listOf(
        BottomBarItem(
            route = QuickNotesRoute,
            label = "QuickNotes",
            icon = Icons.Default.Home
        ),
        BottomBarItem(
            route = CategoriesRoute,
            label = "Categories",
            icon = Icons.Default.Search
        )
    )

    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        bottomBarItems.forEach { item ->
            val isSelected = currentDestination?.hierarchy?.any {
                it.route?.startsWith(item.route::class.qualifiedName!!) == true
            } == true

            NavigationBarItem(
                selected = isSelected,
                onClick = { onItemClick(item.route) },
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) }
            )
        }
    }
}