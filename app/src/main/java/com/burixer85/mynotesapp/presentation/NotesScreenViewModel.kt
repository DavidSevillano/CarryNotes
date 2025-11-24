package com.burixer85.mynotesapp.presentation

import androidx.compose.animation.core.copy
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.burixer85.mynotesapp.R
import com.burixer85.mynotesapp.data.application.RoomApplication
import com.burixer85.mynotesapp.data.entity.toPresentation
import com.burixer85.mynotesapp.presentation.model.Category
import com.burixer85.mynotesapp.presentation.model.Note
import com.burixer85.mynotesapp.presentation.model.QuickNote

import com.burixer85.mynotesapp.presentation.model.toEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NotesScreenViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {

    constructor() : this(SavedStateHandle())

    private val _uiState = MutableStateFlow(NotesUI(isLoading = true))
    val uiState: StateFlow<NotesUI> = _uiState

    val categoryId: Int? = savedStateHandle.get<Int>("categoryId")

    init {
        categoryId?.let { id ->
            loadCategoryAndNotes(id)
        }
    }

    fun loadCategoryAndNotes(categoryId: Int) {
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
            categoryId?.let { loadCategoryAndNotes(it) }
        }
    }

    fun deleteCategory(category: Category) {
        viewModelScope.launch(Dispatchers.IO) {
            RoomApplication.db.categoryDao().deleteCategory(category.toEntity())
        }
    }

    fun addNote(note: Note, categoriesViewModel: CategoriesScreenViewModel) {
        viewModelScope.launch(Dispatchers.IO) {
            val noteEntity = note.toEntity()
            RoomApplication.db.noteDao().insertNote(noteEntity)

            if (categoryId == noteEntity.categoryId) {
                loadCategoryAndNotes(categoryId)
            }

            categoriesViewModel.loadCategories()
        }
    }

    fun updateNote(note: Note, originalCategoryId: Int, categoriesViewModel: CategoriesScreenViewModel) {
        viewModelScope.launch(Dispatchers.IO) {
            RoomApplication.db.noteDao().updateNote(
                id = note.id,
                title = note.title,
                content = note.content,
                categoryId = note.categoryId
            )

            val currentScreenCategoryId = categoryId

            if (currentScreenCategoryId == originalCategoryId) {
                loadCategoryAndNotes(originalCategoryId)
            }

            else if (currentScreenCategoryId == note.categoryId) {
                loadCategoryAndNotes(note.categoryId)
            }

           categoriesViewModel.loadCategories()
        }
    }


    fun deleteNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            val noteEntity = note.toEntity()
            RoomApplication.db.noteDao().deleteNote(noteEntity)
            categoryId?.let { loadCategoryAndNotes(it) }
        }
    }

    class Factory(private val categoryId: Int) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
            val savedStateHandle = extras.createSavedStateHandle()
            savedStateHandle["categoryId"] = categoryId
            return NotesScreenViewModel(savedStateHandle) as T
        }
    }

}


data class NotesUI(
    val category: Category? = null,
    val notes: List<Note> = emptyList(),
    val isLoading: Boolean = false,
    val isNoteDeleted: Boolean = false
)