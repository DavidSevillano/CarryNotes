package com.burixer85.mynotesapp.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.burixer85.mynotesapp.R
import com.burixer85.mynotesapp.presentation.components.CarryAllQuickNotes
import com.burixer85.mynotesapp.presentation.components.CarryNoteDialog
import com.burixer85.mynotesapp.presentation.components.CarryCreateQuickNoteDialog
import com.burixer85.mynotesapp.presentation.model.QuickNote

@Composable
fun QuickNotesScreen(
    modifier: Modifier = Modifier,
    quickNotesScreenViewModel: QuickNotesScreenViewModel,
    onAddQuickNoteClick: () -> Unit
) {
    val uiState by quickNotesScreenViewModel.uiState.collectAsStateWithLifecycle()
    var showQuickNoteDialog by remember { mutableStateOf(false) }
    var showEditQuickNoteDialog by remember { mutableStateOf(false) }
    var selectedQuickNote by remember { mutableStateOf<QuickNote?>(null) }
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    val sharedViewModel: SharedViewModel = viewModel()

    LaunchedEffect(Unit) {
        sharedViewModel.dataChanged.collect {
            quickNotesScreenViewModel.loadQuickNotes()
        }
    }

    LaunchedEffect(uiState.isQuickNoteDeleted) {
        if (uiState.isQuickNoteDeleted) {
            snackbarHostState.showSnackbar(
                message = context.getString(R.string.QuickNotes_Screen_Text_Text_SnackBar),
                duration = SnackbarDuration.Short
            )
            quickNotesScreenViewModel.quickNoteDeleted()
        }
    }

    Box(modifier = modifier
        .fillMaxSize()
        .background(Color(0xFF212121))) {
        if (!uiState.isLoading) {
            Column(
                Modifier
                    .fillMaxSize()
            ) {

                CarryAllQuickNotes(
                    quickNotes = uiState.quickNotes,
                    onQuickNoteClick = { quicknote ->
                        selectedQuickNote = quicknote
                        showQuickNoteDialog = true
                    },
                    onAddQuickNoteClick = onAddQuickNoteClick
                )
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        if (showQuickNoteDialog && selectedQuickNote != null) {
            CarryNoteDialog(
                note = selectedQuickNote!!,
                onDismiss = {
                    showQuickNoteDialog = false
                    selectedQuickNote = null
                },
                onEdit = {
                    showQuickNoteDialog = false
                    showEditQuickNoteDialog = true
                },
                onDeleteConfirm = {
                    quickNotesScreenViewModel.deleteQuickNote(selectedQuickNote!!)
                    selectedQuickNote = null
                    showQuickNoteDialog = false
                }
            )
        }
        if (showEditQuickNoteDialog && selectedQuickNote != null) {
            CarryCreateQuickNoteDialog(
                initialTitle = selectedQuickNote?.title,
                initialContent = selectedQuickNote?.content,
                onDismiss = {
                    showEditQuickNoteDialog = false
                    selectedQuickNote = null
                },
                onConfirm = { title, content ->
                    val updatedQuickNote = selectedQuickNote!!.copy(
                        title = title,
                        content = content
                    )
                    quickNotesScreenViewModel.updateQuickNote(updatedQuickNote)

                    showEditQuickNoteDialog = false
                    selectedQuickNote = null
                }
            )
        }
    }
}


