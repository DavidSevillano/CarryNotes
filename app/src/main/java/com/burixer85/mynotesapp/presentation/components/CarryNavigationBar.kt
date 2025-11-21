package com.burixer85.mynotesapp.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.burixer85.mynotesapp.presentation.navigation.CategoriesRoute
import com.burixer85.mynotesapp.presentation.navigation.QuickNotesRoute
import com.burixer85.mynotesapp.R

private data class BottomBarItem(
    val route: Any,
    val label: String,
    val imageVector: ImageVector? = null,
    val painter: Painter? = null
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
            painter = painterResource(id = R.drawable.quicknote_icon)
        ),
        BottomBarItem(
            route = CategoriesRoute,
            label = "Categories",
            imageVector = Icons.Outlined.Category
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
                icon = {
                    if (item.imageVector != null) {
                        Icon(
                            imageVector = item.imageVector,
                            contentDescription = item.label
                        )
                    } else if (item.painter != null) {
                        Icon(
                            painter = item.painter,
                            contentDescription = item.label
                        )
                    }
                },
                label = { Text(item.label) }
            )
        }
    }
}