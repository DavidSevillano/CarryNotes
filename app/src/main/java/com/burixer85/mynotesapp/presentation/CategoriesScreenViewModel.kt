package com.burixer85.mynotesapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.burixer85.mynotesapp.core.EventType
import com.burixer85.mynotesapp.core.ScreenEvent
import com.burixer85.mynotesapp.data.application.RoomApplication
import com.burixer85.mynotesapp.data.entity.toPresentation
import com.burixer85.mynotesapp.presentation.model.Category
import com.burixer85.mynotesapp.presentation.model.Note
import com.burixer85.mynotesapp.presentation.model.toEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CategoriesScreenViewModel() : ViewModel() {
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

            _uiState.update {
                it.copy(
                    categories = categoriesFromDb,
                    isLoading = false
                )
            }
        }
    }

    fun addNote(note: Note, onComplete: (ScreenEvent) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {

            RoomApplication.db.noteDao().insertNote(note.toEntity())

            onComplete(ScreenEvent.Created(EventType.Note))
        }
    }

    fun addCategory(category: Category, onComplete: (ScreenEvent) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {

            RoomApplication.db.categoryDao().insertCategory(category.toEntity())

            onComplete(ScreenEvent.Created(EventType.Category))

            val updatedCategoriesFromDb = RoomApplication.db.categoryDao().getAllCategories()

            val categoriesForUi = updatedCategoriesFromDb.map { it.toPresentation() }

            _uiState.update {
                it.copy(categories = categoriesForUi)
            }
        }
    }
}

data class CategoriesScreenUI(
    val notes: List<Note> = emptyList(),
    val categories: List<Category> = emptyList(),
    val isLoading: Boolean = false,
)