package com.burixer85.mynotesapp.presentation.navigation


import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.burixer.mynotesapp.presentation.Screens.AchievementsScreen
import com.burixer85.mynotesapp.core.ScreenEvent
import com.burixer85.mynotesapp.core.ex.navigateTo
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
import com.burixer85.mynotesapp.presentation.navigation.NavigationDestination.AchievementsNav
import com.burixer85.mynotesapp.presentation.navigation.NavigationDestination.ErrorNav

@Composable
fun NavigationHost(modifier: Modifier = Modifier) {
    val backStack = rememberNavBackStack(QuickNotesNav)

    val quickNotesViewModel: QuickNotesScreenViewModel = viewModel()
    val categoriesViewModel: CategoriesScreenViewModel = viewModel()
    val sharedViewModel: SharedViewModel = viewModel()

    val requestedRoute by sharedViewModel.currentRoute.collectAsState()
    val fabAction by sharedViewModel.fabAction.collectAsState()

    val currentNavHostRoute = backStack.lastOrNull()


    if (requestedRoute != currentNavHostRoute && !sharedViewModel.isNavigationFromNavHost()) {
        if (requestedRoute is AchievementsNav) {
            backStack.add(requestedRoute)
        } else {
            backStack.replaceAll { requestedRoute }
        }
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
                    onAddQuickNoteClick = { sharedViewModel.onFabOptionSelected("quicknote") },
                    sharedViewModel = sharedViewModel
                )
            }
            entry<CategoriesNav> {
                CategoriesScreen(
                    categoriesScreenViewModel = categoriesViewModel,
                    onCategoryClick = { categoryId, categoryName ->
                        backStack.navigateTo(NotesNav(categoryId, categoryName))
                    },
                    onAddCategoryClick = { sharedViewModel.onFabOptionSelected("category") },
                    sharedViewModel = sharedViewModel
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
                    categoriesViewModel = categoriesViewModel,
                    sharedViewModel = sharedViewModel
                )
            }
            entry<AchievementsNav> { key ->
                AchievementsScreen()
            }
            entry<ErrorNav> {
                Text("Pantalla no encontrada")
            }
        },
        transitionSpec = {

            val forwardAnimation = slideInHorizontally(tween(300)) { it } togetherWith
                    slideOutHorizontally(tween(300)) { -it }

            val backwardAnimation = slideInHorizontally(tween(300)) { -it } togetherWith
                    slideOutHorizontally(tween(300)) { it }

            val inAnimation = slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Up,
                animationSpec = tween(300)
            ) togetherWith fadeOut(animationSpec = tween(300))

            val outAnimation = fadeIn(
                animationSpec = tween(300)
            ) togetherWith slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Down,
                animationSpec = tween(300)
            )

            val fromRouteName = initialState.key.toString()
            val toRouteName = targetState.key.toString()

            when {
                // CASO 1: QuickNotes -> Categories (Lateral)
                fromRouteName.startsWith(QuickNotesNav::class.simpleName!!) &&
                        toRouteName.startsWith(CategoriesNav::class.simpleName!!) -> {
                    forwardAnimation
                }


                // CASO 2: Categories -> Notes (Adentro)
                fromRouteName.startsWith(CategoriesNav::class.simpleName!!) &&
                        toRouteName.startsWith(NotesNav::class.simpleName!!) -> {
                    inAnimation
                }

                // CASO 3: Notes -> Categories (Afuera)
                fromRouteName.startsWith(NotesNav::class.simpleName!!) &&
                        toRouteName.startsWith(CategoriesNav::class.simpleName!!) -> {
                    outAnimation
                }

                // CASO 4: Cualquier Screen -> Achievements (Adentro)
                toRouteName.startsWith(AchievementsNav::class.simpleName!!) -> {
                    inAnimation
                }

                // CASO 5: Achievements -> Cualquier Screen (Afuera)
                fromRouteName.contains(AchievementsNav::class.simpleName!!) -> {
                    outAnimation
                }

                else -> {
                    // CASO 6: Si no es alguno de esos casos específicos, usa la animacion que queda
                    backwardAnimation
                }
            }
        },
        popTransitionSpec = {

            val outAnimation = fadeIn(
                animationSpec = tween(300)
            ) togetherWith slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Down,
                animationSpec = tween(300)
            )

            val backwardAnimation = slideInHorizontally(tween(300)) { -it } togetherWith
                    slideOutHorizontally(tween(300)) { it }

            val fromRouteName = initialState.key.toString()
            val toRouteName = targetState.key.toString()

            when {

                // CASO 1: Achievements -> Cualquier Screen (Afuera)
                fromRouteName.startsWith(AchievementsNav::class.simpleName!!) -> {
                    outAnimation
                }

                // CASO 2: Notes -> Categories (Afuera)
                fromRouteName.startsWith(NotesNav::class.simpleName!!) &&
                        toRouteName.startsWith(CategoriesNav::class.simpleName!!) -> {
                    outAnimation
                }

                else -> {
                    backwardAnimation
                }
            }


        }
    )

    when (fabAction) {
        "quicknote" -> {
            CarryCreateQuickNoteDialog(
                onDismiss = { sharedViewModel.onFabActionConsumed() },
                onConfirm = { title, content ->
                    val onCompleteLambda: (ScreenEvent) -> Unit = { event ->
                        sharedViewModel.postEvent(event)
                    }
                    quickNotesViewModel.addQuickNote(
                        QuickNote(title = title, content = content),
                        onComplete = onCompleteLambda

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
                    val onCompleteLambda: (ScreenEvent) -> Unit = { event ->
                        sharedViewModel.postEvent(event)
                    }

                    categoriesViewModel.addCategory(category,
                    onComplete = onCompleteLambda
                    )

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
                    val onCompleteLambda: (ScreenEvent) -> Unit = { event ->
                        sharedViewModel.postEvent(event)
                    }

                    val newNote = Note(title = title, content = content, categoryId = categoryId)

                    categoriesViewModel.addNote(newNote, onCompleteLambda)

                    sharedViewModel.notifyDataChanged()

                    sharedViewModel.onFabActionConsumed()
                },
                onCreateCategoryRequest = {
                    sharedViewModel.onFabActionConsumed()
                    sharedViewModel.onFabOptionSelected("category")
                }
            )
        }
    }
}