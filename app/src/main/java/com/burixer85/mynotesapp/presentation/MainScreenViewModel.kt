package com.burixer85.mynotesapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.burixer85.mynotesapp.data.application.RoomApplication
import com.burixer85.mynotesapp.presentation.model.Category
import com.burixer85.mynotesapp.presentation.model.Note
import com.burixer85.mynotesapp.presentation.model.QuickNote
import com.burixer85.mynotesapp.presentation.model.toEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainScreenViewModel() : ViewModel() {

    private val _uiState = MutableStateFlow(MainScreenUI())
    val uiState: StateFlow<MainScreenUI> = _uiState

    // Prueba, no estará para la aplicación final ya que es muy ineficiente
    init {
        val quickNotes =listOf(
            QuickNote("Código puzzle", "Contenido de la nota rapida 1"),
            QuickNote("Segundos restantes", "Contenido de la nota rapida 2"),
            QuickNote("Nombre amigo", "Contenido de la nota rapida 3")
        )
        val notes = listOf(
            Note(1, "1", "Contenido de la nota 1"),
            Note(2, "2", "Contenido de la nota 2"),
            Note(3, "3", "Contenido de la nota 3")
        )
        val notes2 = listOf(
            Note(1, "1", "Contenido de otra nota 1"),
            Note(2, "2", "Contenido de otra nota 2"),
            Note(3, "3", "Contenido de otra nota 3")
        )
        val categories = listOf(
            Category(1, "Cumpleaños", notes),
            Category(2, "Recetas", notes2)
        )

        viewModelScope.launch(Dispatchers.IO) {
            val quickNoteEntities = quickNotes.map { quickNote ->
                quickNote.toEntity()
            }
            quickNoteEntities.forEach { quickNoteEntity ->
                RoomApplication.db.quickNoteDao().insertQuickNote(quickNoteEntity)
            }

            val categoryEntities = categories.map { category ->
                category.toEntity()
            }

            categoryEntities.forEach { categoryEntity ->
                RoomApplication.db.categoryDao().insertCategory(categoryEntity)
            }

            categories.forEach { category ->
                category.notes.forEach { note ->
                    val noteEntity = note.toEntity(categoryId = category.id)
                    RoomApplication.db.noteDao().insertNote(noteEntity) // ESTO ES SOLO DE PRUEBA
                }
            }

        }
    }
}

data class MainScreenUI (
    val quickNotes: List<QuickNote> = emptyList(),
    val notes: List<Note> = emptyList(),
    val categories: List<Category> = emptyList(),
    val isLoading: Boolean = false,
)