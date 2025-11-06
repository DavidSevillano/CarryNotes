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
import kotlinx.coroutines.launch

class QuickNotesScreenViewModel() : ViewModel() {

    private val _uiState = MutableStateFlow(QuickNotesUI())
    val uiState: StateFlow<QuickNotesUI> = _uiState

    // Prueba, no estará para la aplicación final

    init {
        viewModelScope.launch(Dispatchers.IO) {

            val existingQuickNotes = RoomApplication.db.quickNoteDao().getAllQuickNotes()
            if (existingQuickNotes.isEmpty()) {
                val quickNotes = listOf(
                    QuickNote("Código puzzle", "Contenido de la nota rapida 1"),
                    QuickNote("Segundos restantes", "Contenido de la nota rapida 2"),
                    QuickNote("Nombre amigo", "Contenido de la nota rapida 3"),
                    QuickNote("Segundos restantes", "Contenido de la nota rapida 2"),
                    QuickNote("Nombre amigo", "Contenido de la nota rapida 3"),

                )

                val quickNoteEntities = quickNotes.map { it.toEntity() }
                quickNoteEntities.forEach { RoomApplication.db.quickNoteDao().insertQuickNote(it) }

            }
            val quickNotesFromDb = RoomApplication.db.quickNoteDao().getAllQuickNotes()

            val quickNotesForUi = quickNotesFromDb.map { it.toPresentation() }

            _uiState.value = QuickNotesUI(
                quickNotes = quickNotesForUi,
            )
        }
    }
}
data class QuickNotesUI (
    val quickNotes: List<QuickNote> = emptyList(),
    val isLoading: Boolean = false,
)