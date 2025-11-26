package com.burixer85.mynotesapp.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.burixer85.mynotesapp.R
import com.burixer85.mynotesapp.presentation.components.CarryAllNotes
import com.burixer85.mynotesapp.presentation.components.CarryCreateCategoryDialog
import com.burixer85.mynotesapp.presentation.components.CarryCreateNoteDialog
import com.burixer85.mynotesapp.presentation.components.CarryCreateQuickNoteDialog
import com.burixer85.mynotesapp.presentation.components.CarryNoteDialog
import com.burixer85.mynotesapp.presentation.model.Note

@Composable
fun NotesScreen(
    modifier: Modifier = Modifier,
    notesScreenViewModel: NotesScreenViewModel = viewModel(),
    onNavigateBackToCategories: () -> Unit,
    sharedViewModel: SharedViewModel,
    categoriesViewModel: CategoriesScreenViewModel
) {
    val uiState by notesScreenViewModel.uiState.collectAsStateWithLifecycle()
    var showNoteDialog by remember { mutableStateOf(false) }
    var showEditNoteDialog by remember { mutableStateOf(false) }

    var selectedNote by remember { mutableStateOf<Note?>(null) }

    var showEditCategoryDialog by remember { mutableStateOf(false) }

    val categoriesUiState by categoriesViewModel.uiState.collectAsStateWithLifecycle()

    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        sharedViewModel.noteAddedFlow.collect { categoryId ->
            if (notesScreenViewModel.categoryId == categoryId) {
                notesScreenViewModel.loadCategoryAndNotes(categoryId)
            }
            categoriesViewModel.loadCategories()
        }
    }

//    LaunchedEffect(uiState.isQuickNoteDeleted) {
//        if (uiState.isQuickNoteDeleted) {
//            snackbarHostState.showSnackbar(
//                message = context.getString(R.string.Notes_Screen_Text_Text_SnackBar),
//                duration = SnackbarDuration.Short
//            )
//            NotesScreenViewModel.NoteDeleted()
//        }
//    }

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
                notesScreenViewModel.deleteNote(selectedNote!!)
                selectedNote = null
                showNoteDialog = false
            }
        )
    }

    if (showEditNoteDialog && selectedNote != null) {
        CarryCreateNoteDialog(
            categories = categoriesUiState.categories,
            initialTitle = selectedNote?.title,
            initialContent = selectedNote?.content,
            initialCategory = categoriesUiState.categories.find { it.id == selectedNote?.categoryId },
            onDismiss = {
                showEditNoteDialog = false
                selectedNote = null
            },
            onConfirm = { title, content, categoryId ->
                val noteToUpdate = selectedNote
                if (noteToUpdate == null) {
                    notesScreenViewModel.addNote(
                        Note(title = title, content = content, categoryId = categoryId),
                        categoriesViewModel
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
                        categoriesViewModel
                    )

                    showEditNoteDialog = false
                    selectedNote = null
                }
                showEditNoteDialog = false
                selectedNote = null
            }
        )
    }

    if (showEditCategoryDialog) {
        uiState.category?.let { categoryToEdit ->
            CarryCreateCategoryDialog(
                categoryToEdit = categoryToEdit,
                onDismiss = { showEditCategoryDialog = false },
                onConfirm = { updatedCategory ->
                    notesScreenViewModel.updateCategory(updatedCategory)
                    showEditCategoryDialog = false
                },
                onDeleteConfirm = {
                    notesScreenViewModel.deleteCategory(categoryToEdit)
                    showEditCategoryDialog = false
                    onNavigateBackToCategories()
                }
            )
        }
    }
}
