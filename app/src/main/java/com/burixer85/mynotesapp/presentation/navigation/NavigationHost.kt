package com.burixer85.mynotesapp.presentation.navigation


import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.burixer85.mynotesapp.presentation.CategoriesScreen
import com.burixer85.mynotesapp.presentation.CategoriesScreenViewModel
import com.burixer85.mynotesapp.presentation.NotesScreen
import com.burixer85.mynotesapp.presentation.NotesScreenViewModel
import com.burixer85.mynotesapp.presentation.QuickNotesScreen
import com.burixer85.mynotesapp.presentation.QuickNotesScreenViewModel
import com.burixer85.mynotesapp.presentation.SharedViewModel
import com.burixer85.mynotesapp.presentation.components.CarryCreateCategoryDialog
import com.burixer85.mynotesapp.presentation.components.CarryCreateNoteDialog
import com.burixer85.mynotesapp.presentation.components.CarryCreateQuickNoteDialog
import com.burixer85.mynotesapp.presentation.model.Note
import com.burixer85.mynotesapp.presentation.model.QuickNote
import com.burixer85.mynotesapp.presentation.navigation.NavigationDestination.QuickNotesNav
import com.burixer85.mynotesapp.presentation.navigation.NavigationDestination.CategoriesNav
import com.burixer85.mynotesapp.presentation.navigation.NavigationDestination.NotesNav
import com.burixer85.mynotesapp.presentation.navigation.NavigationDestination.ErrorNav

@Composable
fun NavigationHost(modifier: Modifier = Modifier) {
    val backStack = rememberNavBackStack(QuickNotesNav)

    val quickNotesViewModel: QuickNotesScreenViewModel = viewModel()
    val categoriesViewModel: CategoriesScreenViewModel = viewModel()
    val sharedViewModel: SharedViewModel = viewModel()
    val coroutineScope = rememberCoroutineScope()

    val requestedRoute by sharedViewModel.currentRoute.collectAsState()
    val fabAction by sharedViewModel.fabAction.collectAsState()

    val currentNavHostRoute = backStack.lastOrNull()

    if (requestedRoute != currentNavHostRoute && !sharedViewModel.isNavigationFromNavHost()) {
        backStack.replaceAll { requestedRoute }
    }

    val lastSeenRoute = remember(currentNavHostRoute) {
        if (currentNavHostRoute != null) {
            sharedViewModel.setCurrentRoute(currentNavHostRoute as NavigationDestination, fromNavHost = true)
        }
        currentNavHostRoute
    }

    val categoriesUiState by categoriesViewModel.uiState.collectAsState()
    val categories = categoriesUiState.categories

    NavDisplay(
        modifier = modifier,
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = entryProvider {
            entry<QuickNotesNav> {
                QuickNotesScreen(
                    quickNotesScreenViewModel = quickNotesViewModel,
                    onAddQuickNoteClick = { sharedViewModel.onFabOptionSelected("quicknote") }
                )
            }
            entry<CategoriesNav> {
                CategoriesScreen(
                    categoriesScreenViewModel = categoriesViewModel,
                    onCategoryClick = { categoryId, categoryName ->
                        backStack.add(NotesNav(categoryId, categoryName))
                    },
                    onAddCategoryClick = { sharedViewModel.onFabOptionSelected("category") }
                )
            }
            entry<NotesNav> { key ->
                val notesScreenViewModel: NotesScreenViewModel = viewModel(
                    key = "notes_vm_${key.categoryId}",
                    factory = NotesScreenViewModel.Factory(key.categoryId)
                )

                NotesScreen(
                    notesScreenViewModel = notesScreenViewModel,
                    onNavigateBackToCategories = { backStack.removeLastOrNull() },
                    categoriesViewModel = categoriesViewModel
                )
            }
            entry<ErrorNav> {
                Text("Pantalla no encontrada")
            }
        }
    )

    when (fabAction) {
        "quicknote" -> {
            CarryCreateQuickNoteDialog(
                onDismiss = { sharedViewModel.onFabActionConsumed() },
                onConfirm = { title, content ->
                    quickNotesViewModel.addQuickNote(
                        QuickNote(title = title, content = content)
                    )
                    sharedViewModel.notifyDataChanged()

                    sharedViewModel.onFabActionConsumed()
                }
            )
        }
        "category" -> {
            CarryCreateCategoryDialog(
                onDismiss = { sharedViewModel.onFabActionConsumed() },
                onConfirm = { category ->
                    categoriesViewModel.addCategory(category)

                    sharedViewModel.notifyDataChanged()

                    sharedViewModel.onFabActionConsumed()
                },
                categoryToEdit = null,
                onDeleteConfirm = {}
            )
        }
        "note" -> {
            val initialCategoryId = (currentNavHostRoute as? NotesNav)?.categoryId
            CarryCreateNoteDialog(
                categories = categories,
                initialCategory = initialCategoryId?.let { id -> categories.find { it.id == id } },
                onDismiss = { sharedViewModel.onFabActionConsumed() },
                onConfirm = { title, content, categoryId ->
                    val newNote = Note(title = title, content = content, categoryId = categoryId)

                    categoriesViewModel.addNote(newNote)

                    sharedViewModel.notifyDataChanged()

                    sharedViewModel.onFabActionConsumed()
                }
            )
        }
    }
}