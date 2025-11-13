package com.burixer85.mynotesapp.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.toRoute
import com.burixer85.mynotesapp.presentation.CategoriesScreen
import com.burixer85.mynotesapp.presentation.NotesScreen
import com.burixer85.mynotesapp.presentation.NotesScreenViewModel
import com.burixer85.mynotesapp.presentation.QuickNotesScreen
import com.burixer85.mynotesapp.presentation.QuickNotesScreenViewModel

@Composable
fun AppNavHost(
    navController: NavHostController,
    scaffoldPadding: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = QuickNotesRoute
    ) {
        composable<QuickNotesRoute> {
            QuickNotesScreen(scaffoldPadding = scaffoldPadding)
        }

        composable<CategoriesRoute> {
            CategoriesScreen(
                scaffoldPadding = scaffoldPadding,
                onCategoryClick = { categoryId, categoryName ->
                    navController.navigate(NotesRoute(
                        categoryId = categoryId,
                        categoryName = categoryName
                    ))
                }
            )
        }

        composable<NotesRoute> { backStackEntry ->
            val notesScreenViewModel: NotesScreenViewModel = viewModel()
            val routeArgs: NotesRoute = backStackEntry.toRoute()

            NotesScreen(
                scaffoldPadding = scaffoldPadding,
                notesScreenViewModel = notesScreenViewModel,
                categoryName = routeArgs.categoryName,
                onNavigateUp = { navController.navigateUp() }
            )
        }
    }
}