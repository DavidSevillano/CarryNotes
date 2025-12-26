package com.burixer85.mynotesapp.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.burixer.mynotesapp.data.manager.AchievementManager
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

class NotesScreenViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {

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

    fun updateCategory(category: Category, onComplete: (ScreenEvent) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            RoomApplication.db.categoryDao().updateCategory(category.toEntity())

            onComplete(ScreenEvent.Updated(EventType.Category))

            categoryId?.let { loadCategoryAndNotes(it) }
        }
    }

    fun deleteCategory(category: Category, onComplete: (ScreenEvent) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            RoomApplication.db.categoryDao().deleteCategory(category.toEntity())

            onComplete(ScreenEvent.Deleted(EventType.Category))

        }
    }

    fun addNote(note: Note, categoriesViewModel: CategoriesScreenViewModel, onComplete: (ScreenEvent) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val noteEntity = note.toEntity()

            RoomApplication.db.noteDao().insertNote(noteEntity)

            val manager = AchievementManager(RoomApplication.db)

            manager.checkNoteAchievements()
            manager.checkGlobalAchievements()

            onComplete(ScreenEvent.Created(EventType.Note))

            if (categoryId == noteEntity.categoryId) {
                loadCategoryAndNotes(categoryId)
            }
            categoriesViewModel.loadCategories()
        }
    }

    fun updateNote(note: Note, originalCategoryId: Int, categoriesViewModel: CategoriesScreenViewModel, onComplete: (ScreenEvent) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {

            RoomApplication.db.noteDao().updateNote(
                id = note.id,
                title = note.title,
                content = note.content,
                categoryId = note.categoryId
            )

            onComplete(ScreenEvent.Updated(EventType.Note))

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


    fun deleteNote(note: Note, onComplete: (ScreenEvent) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            RoomApplication.db.noteDao().deleteNote(note.toEntity())

            val manager = AchievementManager(RoomApplication.db)
            manager.checkDeleteAchievement()
            manager.checkGlobalAchievements()

            onComplete(ScreenEvent.Deleted(EventType.Note))

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
)