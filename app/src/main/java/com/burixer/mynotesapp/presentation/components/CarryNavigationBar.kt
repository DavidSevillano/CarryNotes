package com.burixer85.mynotesapp.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.burixer85.mynotesapp.R
import com.burixer85.mynotesapp.presentation.navigation.NavigationDestination

private data class BottomBarItem(
    val route: Any,
    val label: String,
    val imageVector: ImageVector? = null,
    val painter: Painter? = null
)
@Composable
fun CarryNavigationBar(
    currentDestination: NavigationDestination,
    onItemClick: (NavigationDestination) -> Unit
) {

    val items = listOf(
        NavigationDestination.QuickNotesNav,
        NavigationDestination.CategoriesNav
    )

    NavigationBar(
        containerColor = Color(0xFF212121),
    ) {
        items.forEach { destination ->
            val isSelected = currentDestination::class == destination::class

            NavigationBarItem(
                selected = isSelected,
                onClick = { onItemClick(destination) },
                icon = {
                   when (destination) {
                        is NavigationDestination.QuickNotesNav -> {
                            Icon(
                                painter = painterResource(id = R.drawable.quicknote_icon),
                                contentDescription = "Icono de notas rápidas"
                            )
                        }
                        is NavigationDestination.CategoriesNav -> {
                            Icon(
                                imageVector = Icons.Outlined.Category,
                                contentDescription = "Icono de categorías"
                            )
                        }
                        else -> {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_launcher_background),
                                contentDescription = "Error"
                            )
                        }
                    }
                },
                label = {
                    val labelText = when (destination) {
                        is NavigationDestination.QuickNotesNav -> stringResource(R.string.Main_Screen_Bottom_Navigation_Bar_QuickNotes)
                        is NavigationDestination.CategoriesNav -> stringResource(R.string.Main_Screen_Bottom_Navigation_Bar_Categories)
                        else -> "Error"
                    }
                    Text(text = labelText)
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.White,
                    selectedTextColor = Color.White,
                    unselectedIconColor = Color.Gray,
                    unselectedTextColor = Color.Gray,
                    indicatorColor = Color(0xFF424242)
                )
            )
        }
    }
}