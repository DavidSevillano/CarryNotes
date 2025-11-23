package com.burixer85.mynotesapp.presentation

import android.R.attr.category
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
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CategoriesScreenViewModel() : ViewModel(){
    private val _uiState = MutableStateFlow(CategoriesScreenUI())
    val uiState: StateFlow<CategoriesScreenUI> = _uiState


    init {
        loadCategories()
    }

    fun loadCategories() {
        viewModelScope.launch(Dispatchers.IO) {

            _uiState.update { it.copy(isLoading = true) }


            val categoriesFromDb = RoomApplication.db.categoryDao().getAllCategories()
                .map { category ->
                    category.toPresentation()
                }

            if (categoriesFromDb.isEmpty()) {
                addMockDataAndReload()
            } else {
                _uiState.update {
                    it.copy(
                        categories = categoriesFromDb,
                        isLoading = false
                    )
                }
            }
        }
    }

    fun addCategory(category: Category) {
        viewModelScope.launch(Dispatchers.IO) {
            val categoryEntity = category.toEntity()
            RoomApplication.db.categoryDao().insertCategory(categoryEntity)

            val updatedCategoriesFromDb = RoomApplication.db.categoryDao().getAllCategories()

            val categoriesForUi = updatedCategoriesFromDb.map { it.toPresentation() }

            _uiState.update {
                it.copy(categories = categoriesForUi)
            }
            loadCategories()
        }
    }

    // Prueba, no estará para la aplicación final

    fun addMockDataAndReload() {
        viewModelScope.launch(Dispatchers.IO) {
            val notes = listOf(
                Note(title = "María", content = "9 de Abril"),
                Note(title = "Jesús", content = "14 de Noviembre"),
                Note(title = "Antonio", content = "3 de Agosto")
            )
            val notes2 = listOf(
                Note(title = "1", content = "Contenido de otra nota 1"),
                Note(title = "2", content = "Contenido de otra nota 2"),
                Note(title = "3", content = "Contenido de otra nota 3")
            )
            val categories = listOf(
                Category(id = 1, name = "Cumpleaños", notes = notes),
                Category(id = 2, name = "Recetas", notes = notes2)
            )

            categories.forEach { category ->
                RoomApplication.db.categoryDao().insertCategory(category.toEntity())
            }

            categories.forEach { category ->
                category.notes.forEach { note ->
                    val noteEntity = note.toEntity()
                    RoomApplication.db.noteDao().insertNote(noteEntity)
                }
            }
            loadCategories()
        }
    }
}

data class CategoriesScreenUI (
    val notes: List<Note> = emptyList(),
    val categories: List<Category> = emptyList(),
    val isLoading: Boolean = false,
)