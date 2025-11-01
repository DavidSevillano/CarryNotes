package com.burixer85.mynotesapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.burixer85.mynotesapp.data.application.RoomApplication
import com.burixer85.mynotesapp.data.entity.toPresentation
import com.burixer85.mynotesapp.presentation.model.Category
import com.burixer85.mynotesapp.presentation.model.Note
import com.burixer85.mynotesapp.presentation.model.QuickNote
import com.burixer85.mynotesapp.presentation.model.toEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CategoriesScreenViewModel() : ViewModel(){
    private val _uiState = MutableStateFlow(CategoriesScreenUI())
    val uiState: StateFlow<CategoriesScreenUI> = _uiState

    // Prueba, no estará para la aplicación final

    init {
        viewModelScope.launch(Dispatchers.IO) {

            val existingCategores = RoomApplication.db.categoryDao().getAllCategories()
            if (existingCategores.isEmpty()) {
                val notes = listOf(Note(1, "Primera nota", "Contenido de la primera nota"),
                    Note(2, "Segunda nota", "Contenido de la segunda nota"),
                    Note(3, "Tercera nota", "Contenido de la tercera nota"))
                val notes2 = listOf(Note(4, "Cuarta nota", "Contenido de la cuarta nota"),
                    Note(5, "Quinta nota", "Contenido de la quinta nota"),
                    Note(6, "Sexta nota", "Contenido de la sexta nota"))
                val categories = listOf(Category(1, "Cumpleaños", notes), Category(2, "Recetas", notes2))

                val categoryEntities = categories.map { it.toEntity() }
                categoryEntities.forEach { RoomApplication.db.categoryDao().insertCategory(it) }

                categories.forEach { category ->
                    category.notes.forEach { note ->
                        val noteEntity = note.toEntity(categoryId = category.id)
                        RoomApplication.db.noteDao().insertNote(noteEntity)
                    }
                }
            }
            val categoriesFromDb = RoomApplication.db.categoryDao().getAllCategories()
            val notesFromDb = RoomApplication.db.noteDao().getAllNotes()

            val categoriesForUi = categoriesFromDb.map { categoryEntity ->
                val notesForThisCategory = notesFromDb
                    .filter { noteEntity -> noteEntity.categoryId == categoryEntity.id }
                    .map { it.toPresentation() }
                categoryEntity.toPresentation().copy(notes = notesForThisCategory)
            }

            _uiState.value = CategoriesScreenUI(
                categories = categoriesForUi
            )
        }
    }
}
data class CategoriesScreenUI (
    val notes: List<Note> = emptyList(),
    val categories: List<Category> = emptyList(),
    val isLoading: Boolean = false,
)