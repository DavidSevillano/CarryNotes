package com.burixer85.mynotesapp.presentation.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import androidx.navigation.toRoute
import com.burixer85.mynotesapp.R
import com.burixer85.mynotesapp.presentation.navigation.AppNavHost
import com.burixer85.mynotesapp.presentation.navigation.CategoriesRoute
import com.burixer85.mynotesapp.presentation.navigation.Destination
import com.burixer85.mynotesapp.presentation.navigation.NotesRoute
import com.burixer85.mynotesapp.presentation.navigation.QuickNotesRoute
import kotlin.reflect.KClass

private data class BottomBarItem(
    val route: Any,
    val graphRoute: String,
    val label: String,
    val icon: ImageVector
)

@Composable
fun CarryNavigationBar() {
    val navController: NavHostController = rememberNavController()

    val bottomBarItems = listOf(
        BottomBarItem(
            route = QuickNotesRoute,
            graphRoute = "quicknotes_route",
            label = "QuickNotes",
            icon = Icons.Default.Home
        ),
        BottomBarItem(
            route = CategoriesRoute,
            graphRoute = "categories_route",
            label = "Categories",
            icon = Icons.Default.Search
        )
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets.navigationBars,
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                bottomBarItems.forEach { item ->
                    val isSelected =
                        currentDestination?.hierarchy?.any { it.route == item.graphRoute } == true

                    NavigationBarItem(
                        selected = isSelected,
                        onClick = {
                            if (!isSelected) {
                                navController.navigate(item.route) {
                                    popUpTo(item.graphRoute) {
                                        inclusive = true
                                    }
                                    launchSingleTop = true
                                }
                            }
                        },
                        icon = { Icon(item.icon, contentDescription = item.label) },
                        label = { Text(item.label) }
                    )
                }
            }
        }
    ) { innerPadding ->
        AppNavHost(
            navController = navController,
            scaffoldPadding = innerPadding
        )
    }
}