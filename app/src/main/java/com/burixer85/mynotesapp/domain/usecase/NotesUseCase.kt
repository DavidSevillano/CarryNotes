package com.burixer85.mynotesapp.domain.usecase

import com.burixer85.mynotesapp.data.model.NoteModel
import com.burixer85.mynotesapp.data.model.CategoryModel
import com.burixer85.mynotesapp.domain.model.Note
import com.burixer85.mynotesapp.domain.model.Category
import com.burixer85.mynotesapp.domain.repository.INoteRepository

class NotesUseCase(private val repository: INoteRepository) {

    suspend fun getNotes(): List<Note> {
        return repository.getNotes().map {
            Note(
                id = it.id,
                title = it.title,
                content = it.content
            )
        }
    }

    suspend fun addNote(note: Note, categoryId: Int) {
        val noteModel = NoteModel(
            id = note.id,
            title = note.title,
            content = note.content,
            categoryId = categoryId
        )
        repository.addNote(noteModel)
    }

    suspend fun deleteNote(note: Note, categoryId: Int) {
        val noteModel = NoteModel(
            id = note.id,
            title = note.title,
            content = note.content,
            categoryId = categoryId
        )
        repository.removeNote(noteModel)
    }
}