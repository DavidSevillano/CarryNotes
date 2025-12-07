package com.burixer85.mynotesapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.burixer85.mynotesapp.core.EventType
import com.burixer85.mynotesapp.core.ScreenEvent
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

    fun addQuickNote(quickNote: QuickNote, onComplete: (ScreenEvent) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            RoomApplication.db.quickNoteDao().insertQuickNote(quickNote.toEntity())

            val updatedNotesFromDb = RoomApplication.db.quickNoteDao().getAllQuickNotes()

            val quickNotesForUi = updatedNotesFromDb.map { it.toPresentation() }

            onComplete(ScreenEvent.Created(EventType.QuickNote))

            _uiState.update { currentState ->
                currentState.copy(quickNotes = quickNotesForUi)
            }
        }
    }

    fun updateQuickNote(quickNote: QuickNote, onComplete: (ScreenEvent) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {

            RoomApplication.db.quickNoteDao().updateQuickNote(quickNote.toEntity())

            onComplete(ScreenEvent.Updated(EventType.QuickNote))

            _uiState.update { currentState ->
                val updatedList = currentState.quickNotes.map {
                    if (it.id == quickNote.id) quickNote else it
                }
                currentState.copy(quickNotes = updatedList)
            }
        }
    }

    fun deleteQuickNote(quickNote: QuickNote, onComplete: (ScreenEvent) -> Unit){
        viewModelScope.launch(Dispatchers.IO) {
            RoomApplication.db.quickNoteDao().deleteQuickNote(quickNote.toEntity())

            onComplete(ScreenEvent.Deleted(EventType.QuickNote))

            _uiState.update { currentState ->
                val updatedList = currentState.quickNotes.filter { it.id != quickNote.id }
                currentState.copy(quickNotes = updatedList)
            }
        }
    }
}


data class QuickNotesUI (
    val quickNotes: List<QuickNote> = emptyList(),
    val isLoading: Boolean = false,
)
