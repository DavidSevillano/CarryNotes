package com.burixer85.mynotesapp.presentation

import androidx.activity.result.launch
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class SharedViewModel : ViewModel() {

    // Flujo para el NotesScreen

    private val _noteAddedFlow = MutableSharedFlow<Int>(replay = 0)
    val noteAddedFlow = _noteAddedFlow.asSharedFlow()

    suspend fun notifyNoteAdded(categoryId: Int) {
        _noteAddedFlow.emit(categoryId)
    }

    // Flujo para el QuickNoteScreen

    private val _quickNoteUpdatedFlow = MutableSharedFlow<Unit>(replay = 0)
    val quickNoteUpdatedFlow = _quickNoteUpdatedFlow.asSharedFlow()

    fun notifyQuickNoteUpdated() {
        viewModelScope.launch {
            _quickNoteUpdatedFlow.emit(Unit)
        }
    }

}