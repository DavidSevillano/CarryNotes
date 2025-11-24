package com.burixer85.mynotesapp.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.burixer85.mynotesapp.presentation.CategoriesScreen
import com.burixer85.mynotesapp.presentation.CategoriesScreenViewModel
import com.burixer85.mynotesapp.presentation.MainScreen
import com.burixer85.mynotesapp.presentation.NotesScreen
import com.burixer85.mynotesapp.presentation.NotesScreenViewModel
import com.burixer85.mynotesapp.presentation.QuickNotesScreen
import com.burixer85.mynotesapp.presentation.QuickNotesScreenViewModel
import com.burixer85.mynotesapp.presentation.components.CarryCreateCategoryDialog
import com.burixer85.mynotesapp.presentation.components.CarryCreateNoteDialog
import com.burixer85.mynotesapp.presentation.components.CarryCreateQuickNoteDialog
import com.burixer85.mynotesapp.presentation.model.Note
import com.burixer85.mynotesapp.presentation.model.QuickNote

@Composable
fun NavigationWrapper() {
    val navController = rememberNavController()
    var showCreateQuickNoteDialog by remember { mutableStateOf(false) }
    var showCreateCategoryDialog by remember { mutableStateOf(false) }
    var showCreateNoteDialog by remember { mutableStateOf(false) }

    val quickNotesViewModel: QuickNotesScreenViewModel = viewModel()
    val categoriesViewModel: CategoriesScreenViewModel = viewModel()
    val notesViewModel: NotesScreenViewModel = viewModel()

    val categoriesUiState by categoriesViewModel.uiState.collectAsState()

    val categories = categoriesUiState.categories

    MainScreen(
        navController = navController,
        onItemClick = { route ->
            val currentDestination = navController.currentDestination

            if (route is CategoriesRoute && currentDestination?.route?.startsWith(NotesRoute::class.qualifiedName!!) == true) {
                navController.popBackStack()
            } else {
                navController.navigate(route) {
                    popUpTo(navController.graph.findStartDestination().id) {
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        },
        onFabOptionSelected = { option ->
            when (option) {
                "quicknote" -> {
                    showCreateQuickNoteDialog = true
                }

                "category" -> {
                    showCreateCategoryDialog = true
                }

                "note" -> {
                    showCreateNoteDialog = true
                }
            }
        },
    ) { scaffoldPadding ->
        NavHost(
            navController = navController,
            startDestination = QuickNotesRoute,
            modifier = Modifier.padding(scaffoldPadding)
        ) {
            composable<QuickNotesRoute> {

                QuickNotesScreen(
                    modifier = Modifier.zIndex(1f),
                    quickNotesScreenViewModel = quickNotesViewModel,
                    onAddQuickNoteClick = { showCreateQuickNoteDialog = true }
                )
            }

            composable<CategoriesRoute> {

                CategoriesScreen(
                    modifier = Modifier.zIndex(1f),
                    onCategoryClick = { categoryId, categoryName ->
                        navController.navigate(NotesRoute(categoryId, categoryName))
                    },
                    categoriesScreenViewModel = categoriesViewModel,
                    onAddCategoryClick = {
                        showCreateCategoryDialog = true
                    }
                )
            }

            composable<NotesRoute> { backStackEntry ->

                val noteData: NotesRoute = backStackEntry.toRoute()

                val notesScreenViewModel: NotesScreenViewModel = viewModel(
                    factory = NotesScreenViewModel.Factory(noteData.categoryId)
                )

                NotesScreen(
                    modifier = Modifier.zIndex(1f),
                    notesScreenViewModel = notesScreenViewModel,
                    onNavigateBackToCategories = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }

    if (showCreateQuickNoteDialog) {
        CarryCreateQuickNoteDialog(
            initialTitle = null,
            initialContent = null,
            onDismiss = { showCreateQuickNoteDialog = false },
            onConfirm = { title, content ->
                quickNotesViewModel.addQuickNote(
                    QuickNote(
                        title = title,
                        content = content
                    )
                )
                showCreateQuickNoteDialog = false
            }
        )
    }

    if (showCreateNoteDialog) {
        CarryCreateNoteDialog(
            categories = categories,
            onDismiss = { showCreateNoteDialog = false },
            onConfirm = { title, content, categoryId ->
                val newNote = Note(title = title, content = content, categoryId = categoryId)

                notesViewModel.addNote(newNote, categoriesViewModel)

                showCreateNoteDialog = false
            }
        )
    }

    if (showCreateCategoryDialog) {
        CarryCreateCategoryDialog(
            onDismiss = { showCreateCategoryDialog = false },
            onConfirm = { category ->
                categoriesViewModel.addCategory(category)
                showCreateCategoryDialog = false
            },
            categoryToEdit = null,
            onDeleteConfirm = {}
        )
    }

}