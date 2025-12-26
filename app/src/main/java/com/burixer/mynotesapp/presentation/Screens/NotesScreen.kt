package com.burixer85.mynotesapp.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.burixer85.mynotesapp.core.ScreenEvent
import com.burixer85.mynotesapp.presentation.components.CarryAllNotes
import com.burixer85.mynotesapp.presentation.components.CarryCreateCategoryDialog
import com.burixer85.mynotesapp.presentation.components.CarryCreateNoteDialog
import com.burixer85.mynotesapp.presentation.components.CarryNoteDialog
import com.burixer85.mynotesapp.presentation.model.Note

@Composable
fun NotesScreen(
    modifier: Modifier = Modifier,
    notesScreenViewModel: NotesScreenViewModel = viewModel(),
    onNavigateBackToCategories: () -> Unit,
    categoriesViewModel: CategoriesScreenViewModel,
    sharedViewModel: SharedViewModel
) {
    val uiState by notesScreenViewModel.uiState.collectAsStateWithLifecycle()
    val categoriesUiState by categoriesViewModel.uiState.collectAsStateWithLifecycle()

    var showNoteDialog by remember { mutableStateOf(false) }
    var showEditNoteDialog by remember { mutableStateOf(false) }
    var showEditCategoryDialog by remember { mutableStateOf(false) }
    var selectedNote by remember { mutableStateOf<Note?>(null) }

    val onCompleteLambda: (ScreenEvent) -> Unit = { event ->
        sharedViewModel.postEvent(event)
    }

    LaunchedEffect(key1 = notesScreenViewModel.categoryId!!, key2 = sharedViewModel) {

        val currentCategoryId = notesScreenViewModel.categoryId

        notesScreenViewModel.loadCategoryAndNotes(currentCategoryId)

        sharedViewModel.dataChanged.collect {
            notesScreenViewModel.loadCategoryAndNotes(currentCategoryId)
            categoriesViewModel.loadCategories()
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF212121))
    ) {

        if (!uiState.isLoading) {
            val currentCategory = uiState.category!!
            CarryAllNotes(
                notes = currentCategory.notes,
                categoryName = currentCategory.name,
                onNoteClick = { note ->
                    selectedNote = note
                    showNoteDialog = true
                },
                onEditCategoryClick = {
                    showEditCategoryDialog = true
                },
                onAddNoteClick = {
                    selectedNote = null
                    showEditNoteDialog = true
                }
            )
        } else {

            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }

        }
    }

    if (showNoteDialog && selectedNote != null) {
        CarryNoteDialog(
            note = selectedNote!!,
            onDismiss = {
                showNoteDialog = false
                selectedNote = null
            },
            onEdit = {
                showNoteDialog = false
                showEditNoteDialog = true
            },
            onDeleteConfirm = {
                notesScreenViewModel.deleteNote(selectedNote!!, onCompleteLambda)
                selectedNote = null
                showNoteDialog = false
            }
        )
    }

    if (showEditNoteDialog) {
        val currentCategory = uiState.category

        CarryCreateNoteDialog(
            categories = categoriesUiState.categories,
            initialTitle = selectedNote?.title,
            initialContent = selectedNote?.content,
            initialCategory = if (selectedNote != null) {
                categoriesUiState.categories.find { it.id == selectedNote?.categoryId }
            } else {
                currentCategory
            },
            onDismiss = {
                showEditNoteDialog = false
                selectedNote = null
            },
            onConfirm = { title, content, categoryId ->
                val noteToUpdate = selectedNote
                if (noteToUpdate == null) {
                    notesScreenViewModel.addNote(
                        Note(title = title, content = content, categoryId = categoryId),
                        categoriesViewModel, onCompleteLambda
                    )
                } else {
                    val originalCategoryId = noteToUpdate.categoryId
                    val updatedNote = noteToUpdate.copy(
                        title = title,
                        content = content,
                        categoryId = categoryId
                    )

                    notesScreenViewModel.updateNote(
                        updatedNote,
                        originalCategoryId,
                        categoriesViewModel,
                        onCompleteLambda
                    )

                    showEditNoteDialog = false
                    selectedNote = null
                }
                showEditNoteDialog = false
                selectedNote = null
            },
            onCreateCategoryRequest = {}
        )
    }

    if (showEditCategoryDialog) {
        uiState.category?.let { categoryToEdit ->
            CarryCreateCategoryDialog(
                categoryToEdit = categoryToEdit,
                onDismiss = { showEditCategoryDialog = false },
                onConfirm = { updatedCategory ->
                    notesScreenViewModel.updateCategory(updatedCategory, onCompleteLambda)
                    showEditCategoryDialog = false
                },
                onDeleteConfirm = {
                    notesScreenViewModel.deleteCategory(categoryToEdit, onCompleteLambda)
                    showEditCategoryDialog = false
                    onNavigateBackToCategories()
                }
            )
        }
    }
}
