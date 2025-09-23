package com.burixer85.mynotesapp.domain.repository

import com.burixer85.mynotesapp.data.entity.NoteModel
import com.burixer85.mynotesapp.domain.model.Note

interface INoteRepository {
    suspend fun getNotes(): List<Note>
    suspend fun addNote(note: Note)
    suspend fun removeNote(note: Note)
}