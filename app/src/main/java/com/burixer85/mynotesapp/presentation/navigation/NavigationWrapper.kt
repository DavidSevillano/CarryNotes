package com.burixer85.mynotesapp.presentation.navigation

import androidx.activity.result.launch
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.burixer85.mynotesapp.presentation.CategoriesScreen
import com.burixer85.mynotesapp.presentation.CategoriesScreenViewModel
import com.burixer85.mynotesapp.presentation.MainScreen
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
import kotlinx.coroutines.launch

@Composable
fun NavigationWrapper() {
    val navController = rememberNavController()
    var showCreateQuickNoteDialog by remember { mutableStateOf(false) }
    var showCreateCategoryDialog by remember { mutableStateOf(false) }
    var showCreateNoteDialog by remember { mutableStateOf(false) }

    val quickNotesViewModel: QuickNotesScreenViewModel = viewModel()
    val categoriesViewModel: CategoriesScreenViewModel = viewModel()
    val sharedViewModel: SharedViewModel = viewModel()

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

            composable<QuickNotesRoute>(
                enterTransition = {
                        slideIntoContainer(
                            towards = AnimatedContentTransitionScope.SlideDirection.End,
                            animationSpec = tween(400)
                        )
                },
                exitTransition = {
                        slideOutOfContainer(
                            towards = AnimatedContentTransitionScope.SlideDirection.Start,
                            animationSpec = tween(400)
                        )
                },
                popEnterTransition = {
                    slideIntoContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.End,
                        animationSpec = tween(400)
                    )
                },
            ) {
                QuickNotesScreen(
                    modifier = Modifier.zIndex(1f),
                    quickNotesScreenViewModel = quickNotesViewModel,
                    onAddQuickNoteClick = { showCreateQuickNoteDialog = true }
                )
            }

            composable<CategoriesRoute>(
                enterTransition = {
                        slideIntoContainer(
                            towards = AnimatedContentTransitionScope.SlideDirection.Start,
                            animationSpec = tween(400)
                        )
                },
                exitTransition = {
                    if (targetState.destination.route?.startsWith(NotesRoute::class.qualifiedName!!) == true) {
                        fadeOut(animationSpec = tween(0))
                    } else {
                        slideOutOfContainer(
                            towards = AnimatedContentTransitionScope.SlideDirection.End,
                            animationSpec = tween(400)
                        )
                    }
                },
                popEnterTransition = {
                    if (initialState.destination.route?.startsWith(NotesRoute::class.qualifiedName!!) == true) {
                        fadeIn(animationSpec = tween(0))
                    } else {
                        slideIntoContainer(
                            towards = AnimatedContentTransitionScope.SlideDirection.Down,
                            animationSpec = tween(400)
                        )
                    }
                },
                popExitTransition = {
                    slideOutOfContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.End,
                        animationSpec = tween(400)
                    )
                }
            )  {
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

            composable<NotesRoute>(
                enterTransition = {
                    if (initialState.destination.route?.startsWith(CategoriesRoute::class.qualifiedName!!) == true) {
                        fadeIn(animationSpec = tween(0))
                    } else {
                        slideIntoContainer(
                            towards = AnimatedContentTransitionScope.SlideDirection.Up,
                            animationSpec = tween(400)
                        )
                    }
                },
                exitTransition = {
                    if (targetState.destination.route?.startsWith(QuickNotesRoute::class.qualifiedName!!) == true) {
                        slideOutOfContainer(
                            towards = AnimatedContentTransitionScope.SlideDirection.Right,
                            animationSpec = tween(400)
                        )
                    }else{
                        slideOutOfContainer(
                            towards = AnimatedContentTransitionScope.SlideDirection.Down,
                            animationSpec = tween(100)
                        )
                    }
                },
                popExitTransition = {
                    if (targetState.destination.route?.startsWith(CategoriesRoute::class.qualifiedName!!) == true) {
                        fadeOut(animationSpec = tween(0))
                    } else {
                        slideOutOfContainer(
                            towards = AnimatedContentTransitionScope.SlideDirection.Down,
                            animationSpec = tween(100)
                        )
                    }
                }
            ) { backStackEntry ->

                val noteData: NotesRoute = backStackEntry.toRoute()

                val notesScreenViewModel: NotesScreenViewModel = viewModel(
                    factory = NotesScreenViewModel.Factory(noteData.categoryId)
                )

                NotesScreen(
                    modifier = Modifier.zIndex(1f),
                    notesScreenViewModel = notesScreenViewModel,
                    onNavigateBackToCategories = { navController.popBackStack() },
                    sharedViewModel = sharedViewModel,
                    categoriesViewModel = categoriesViewModel
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
        val genericNotesViewModel: NotesScreenViewModel = viewModel()
        val coroutineScope = rememberCoroutineScope()

        val backStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = backStackEntry?.destination?.route

        val initialCategoryId: Int? = if (currentRoute?.startsWith(NotesRoute::class.qualifiedName!!) == true) {
            backStackEntry?.toRoute<NotesRoute>()?.categoryId
        } else {
            null
        }

        val initialCategory = initialCategoryId?.let { id ->
            categories.find { it.id == id }
        }

        CarryCreateNoteDialog(
            categories = categories,
            initialCategory = initialCategory,
            onDismiss = { showCreateNoteDialog = false },
            onConfirm = { title, content, categoryId ->
                val newNote = Note(title = title, content = content, categoryId = categoryId)

                genericNotesViewModel.addNote(newNote, categoriesViewModel)
                coroutineScope.launch {
                    sharedViewModel.notifyNoteAdded(categoryId)
                }

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