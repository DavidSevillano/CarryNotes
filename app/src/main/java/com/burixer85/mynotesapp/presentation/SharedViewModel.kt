package com.burixer85.mynotesapp.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class SharedViewModel : ViewModel() {

    private val _noteAddedFlow = MutableSharedFlow<Int>(replay = 0)
    val noteAddedFlow = _noteAddedFlow.asSharedFlow()

    suspend fun notifyNoteAdded(categoryId: Int) {
        _noteAddedFlow.emit(categoryId)
    }
}