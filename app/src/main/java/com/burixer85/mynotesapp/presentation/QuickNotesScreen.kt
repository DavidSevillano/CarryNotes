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
    scaffoldPadding: PaddingValues,
    quickNotesScreenViewModel: QuickNotesScreenViewModel = viewModel()
) {
    val uiState by quickNotesScreenViewModel.uiState.collectAsStateWithLifecycle()
    var showNoteDialog by remember { mutableStateOf(false) }

    var showCreateNoteDialog by remember { mutableStateOf(false) }
    var selectedNote by remember { mutableStateOf<QuickNote?>(null) }

    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current


    LaunchedEffect(uiState.isQuickNoteDeleted) {
        if (uiState.isQuickNoteDeleted) {
            snackbarHostState.showSnackbar(
                message = context.getString(R.string.QuickNotes_Screen_Text_Text_SnackBar),
                duration = SnackbarDuration.Short
            )
            quickNotesScreenViewModel.quickNoteDeleted()
        }
    }
    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            contentWindowInsets = WindowInsets(0, 0, 0, 0),
            containerColor = Color(0xFF212121),
            snackbarHost = {
                SnackbarHost(hostState = snackbarHostState) { message ->
                    Snackbar(
                        modifier = Modifier.padding(12.dp),
                        containerColor = Color(0xFF333333),
                        shape = RoundedCornerShape(14.dp),
                        contentColor = Color.White
                    ) {
                        Text(message.visuals.message)
                    }
                }
            },
            floatingActionButton = {
                CarryFloatingActionButton(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(scaffoldPadding)
                        .padding(16.dp),
                    onOptionSelected = { option ->
                        when (option) {
                            "quicknote" -> {
                                selectedNote = null
                                showCreateNoteDialog = true
                            }

                            "category" -> {
                                //TODO: Implementar lógica para añadir una category
                            }
                        }
                    })
            }

        ) { padding ->
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
                                        .clickable(
                                            onClick = { showCreateNoteDialog = true }
                                        ),
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
                            .fillMaxSize()
                            .padding(padding),
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
                        showCreateNoteDialog = true
                    },
                    onDeleteConfirm = {
                        quickNotesScreenViewModel.deleteQuickNote(selectedNote!!)
                    }
                )
            }
            if (showCreateNoteDialog) {
                CarryCreateQuickNoteDialog(
                    noteToEdit = selectedNote,
                    onDismiss = {
                        showCreateNoteDialog = false
                        selectedNote = null
                    },
                    onConfirm = { note ->
                        if (selectedNote != null) {
                            quickNotesScreenViewModel.updateQuickNote(note)
                        } else {
                            quickNotesScreenViewModel.addQuickNote(note)
                        }
                        showCreateNoteDialog = false
                        selectedNote = null
                    }
                )
            }
        }

    }


}
