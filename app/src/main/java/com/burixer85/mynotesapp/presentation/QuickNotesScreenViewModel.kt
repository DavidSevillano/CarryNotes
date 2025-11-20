package com.burixer85.mynotesapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.burixer85.mynotesapp.data.application.RoomApplication
import com.burixer85.mynotesapp.data.entity.toPresentation

import com.burixer85.mynotesapp.presentation.model.QuickNote
import com.burixer85.mynotesapp.presentation.model.toEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class QuickNotesScreenViewModel() : ViewModel() {

    private val _uiState = MutableStateFlow(QuickNotesUI(isLoading = true))
    val uiState: StateFlow<QuickNotesUI> = _uiState

    init {
        loadQuickNotes()
    }
    fun loadQuickNotes() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update { it.copy(isLoading = true) }

            val notesFromDb = RoomApplication.db.quickNoteDao().getAllQuickNotes()

            val notesForUi = notesFromDb.map { it.toPresentation() }

            _uiState.update {
                it.copy(quickNotes = notesForUi, isLoading = false)
            }
        }
    }

    fun addQuickNote(quickNote: QuickNote) {
        viewModelScope.launch(Dispatchers.IO) {
            val noteEntity = quickNote.toEntity()
            RoomApplication.db.quickNoteDao().insertQuickNote(noteEntity)

            val updatedNotesFromDb = RoomApplication.db.quickNoteDao().getAllQuickNotes()

            val quickNotesForUi = updatedNotesFromDb.map { it.toPresentation() }

            _uiState.update { currentState ->
                currentState.copy(quickNotes = quickNotesForUi)
            }
        }
    }

    fun updateQuickNote(note: QuickNote) {
        viewModelScope.launch(Dispatchers.IO) {
            val noteEntity = note.toEntity()

            RoomApplication.db.quickNoteDao().updateQuickNote(noteEntity)

            _uiState.update { currentState ->
                val updatedList = currentState.quickNotes.map {
                    if (it.id == note.id) note else it
                }
                currentState.copy(quickNotes = updatedList)
            }
        }
    }

    fun deleteQuickNote(note: QuickNote){
        viewModelScope.launch(Dispatchers.IO) {
            val noteEntity = note.toEntity()

            RoomApplication.db.quickNoteDao().deleteQuickNote(noteEntity)

            _uiState.update { currentState ->
                val updatedList = currentState.quickNotes.filter { it.id != note.id }
                currentState.copy(quickNotes = updatedList, isQuickNoteDeleted = true)
            }

        }
    }

    fun quickNoteDeleted() {
        _uiState.update { currentState ->
            currentState.copy(isQuickNoteDeleted = false)
        }
    }

}


data class QuickNotesUI (
    val quickNotes: List<QuickNote> = emptyList(),
    val isLoading: Boolean = false,
    val isQuickNoteDeleted: Boolean = false
)