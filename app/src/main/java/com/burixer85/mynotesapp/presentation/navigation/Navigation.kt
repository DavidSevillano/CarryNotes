package com.burixer85.mynotesapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.burixer85.mynotesapp.presentation.CategoriesScreen
import com.burixer85.mynotesapp.presentation.QuickNotesScreen
import com.burixer85.mynotesapp.presentation.QuickNotesScreenViewModel

@Composable
fun AppNavHost(navController: NavHostController,
               startDestination: Destination, modifier: Modifier) {
    NavHost(navController = navController, startDestination = startDestination.route) {
        Destination.entries.forEach { destination ->
            composable(destination.route) {
                when (destination) {
                    Destination.QUICKNOTE -> QuickNotesScreen(modifier)
                    Destination.CATEGORIES -> CategoriesScreen(modifier)
                }
            }
        }
    }
}