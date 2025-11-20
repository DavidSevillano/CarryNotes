package com.burixer85.mynotesapp.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
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
import com.burixer85.mynotesapp.presentation.components.CarryAllQuickNotes
import com.burixer85.mynotesapp.presentation.components.CarryFloatingActionButton
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
    var showNoteDialog by remember { mutableStateOf(false) }
    var showEditNoteDialog by remember { mutableStateOf(false) }

    var selectedNote by remember { mutableStateOf<QuickNote?>(null) }

    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        quickNotesScreenViewModel.loadQuickNotes()
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

    Box(modifier = modifier.fillMaxSize().background(Color(0xFF212121))) {
        if (!uiState.isLoading) {
            Column(
                Modifier
                    .fillMaxSize()
            ) {
                Text(
                    modifier = Modifier.padding(top = 24.dp, start = 24.dp, end = 24.dp),
                    text = stringResource(R.string.Main_Screen_Text_Tittle),
                    color = Color.White,
                    style = MaterialTheme.typography.headlineSmall
                )
                Box(
                    Modifier
                        .fillMaxWidth()
                        .padding(24.dp)

                        .height(60.dp)
                        .border(
                            BorderStroke(2.dp, Color.White),
                            shape = RoundedCornerShape(32.dp)
                        )
                        .background(
                            color = Color(0xFF303030),
                            shape = RoundedCornerShape(14.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {

                    Text(
                        text = stringResource(R.string.Main_Screen_Button_Achievements),
                        color = Color.White,
                        style = MaterialTheme.typography.labelLarge
                    )

                }
                Spacer(
                    modifier = Modifier.padding(12.dp),
                )
                if (uiState.quickNotes.isNotEmpty()) {
                    CarryAllQuickNotes(
                        quickNotes = uiState.quickNotes,
                        onQuickNoteClick = { quicknote ->
                            selectedNote = quicknote
                            showNoteDialog = true
                        }
                    )
                } else {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = stringResource(R.string.QuickNotes_Screen_Main_Text_No_Quicknotes),
                            color = Color.White,
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier
                                .padding(bottom = 32.dp)
                        )

                        Box(
                            Modifier
                                .fillMaxWidth()
                                .height(240.dp)
                                .padding(12.dp)
                                .border(
                                    BorderStroke(2.dp, Color.White),
                                    shape = RoundedCornerShape(14.dp)
                                )
                                .background(
                                    color = Color(0xFF303030),
                                    shape = RoundedCornerShape(14.dp)
                                )
                                .clickable { onAddQuickNoteClick() },
                            contentAlignment = Alignment.Center
                        ) {

                            Text(
                                text = stringResource(R.string.QuickNotes_Screen_Text_Box_Add_QuickNote),
                                color = Color.White,
                                style = MaterialTheme.typography.labelLarge,
                            )
                        }

                    }
                }
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
                    quickNotesScreenViewModel.deleteQuickNote(selectedNote!!)
                    showNoteDialog = false
                }
            )
        }
        if (showEditNoteDialog && selectedNote != null) {
            CarryCreateQuickNoteDialog(
                noteToEdit = selectedNote,
                onDismiss = {
                    showEditNoteDialog = false
                    selectedNote = null
                },
                onConfirm = { note ->
                    quickNotesScreenViewModel.updateQuickNote(note)
                    showEditNoteDialog = false
                    selectedNote = null
                }
            )
        }
    }
}


