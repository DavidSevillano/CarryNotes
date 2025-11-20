package com.burixer85.mynotesapp.presentation

import androidx.compose.animation.core.copy
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.burixer85.mynotesapp.R
import com.burixer85.mynotesapp.data.application.RoomApplication
import com.burixer85.mynotesapp.data.entity.toPresentation
import com.burixer85.mynotesapp.presentation.model.Category
import com.burixer85.mynotesapp.presentation.model.Note

import com.burixer85.mynotesapp.presentation.model.toEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NotesScreenViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {

    private val _uiState = MutableStateFlow(NotesUI(isLoading = true))
    val uiState: StateFlow<NotesUI> = _uiState

    private val categoryId: Int = savedStateHandle.get<Int>("categoryId")!!

    init {
        loadCategoryAndNotes()
    }

    private fun loadCategoryAndNotes() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update { it.copy(isLoading = true) }
            val categoryFromDb = RoomApplication.db.categoryDao().getCategoryById(categoryId)
            if (categoryFromDb != null) {
                val categoryForUi = categoryFromDb.toPresentation()
                _uiState.update {
                    it.copy(
                        category = categoryForUi,
                        notes = categoryForUi.notes,
                        isLoading = false
                    )
                }
            } else {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    fun updateCategory(category: Category) {
        viewModelScope.launch(Dispatchers.IO) {
            RoomApplication.db.categoryDao().updateCategory(category.toEntity())
            loadCategoryAndNotes()
        }
    }
}


data class NotesUI (
    val category: Category? = null,
    val notes: List<Note> = emptyList(),
    val isLoading: Boolean = false,
    val isNoteDeleted: Boolean = false
)