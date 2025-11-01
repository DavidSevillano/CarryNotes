package com.burixer85.mynotesapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.burixer85.mynotesapp.presentation.QuickNotesScreen
import com.burixer85.mynotesapp.presentation.QuickNotesScreenViewModel

@Composable
fun NavigationWrapper() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = QuickNotesRoute){
        composable<QuickNotesRoute>{
            val quickNotesViewModel: QuickNotesScreenViewModel = viewModel()
            QuickNotesScreen(quickNotesViewModel)
        }
    }

}