package com.burixer85.mynotesapp.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

enum class Destination(val route: String,
                       val label:String,
                       val icon: ImageVector,
                       val contentDescription: String) {
    QUICKNOTE("nav_quicknote","QuickNotes", Icons.Default.Home, "main view"),
    CATEGORIES("nav_categories","Categories", Icons.Default.Search, "categories view")
}