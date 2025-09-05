package com.burixer85.mynotesapp.domain.repository

import com.burixer85.mynotesapp.data.model.NoteModel

interface INoteRepository {
    suspend fun getNotes(): List<NoteModel>
    suspend fun addNote(note: NoteModel)
    suspend fun removeNote(note: NoteModel)
}