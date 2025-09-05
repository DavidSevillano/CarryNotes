package com.burixer85.mynotesapp.domain.usecase

import com.burixer85.mynotesapp.data.model.NoteModel
import com.burixer85.mynotesapp.domain.model.Note
import com.burixer85.mynotesapp.domain.repository.INoteRepository

class NotesUseCase(private val repository: INoteRepository) {

    suspend fun getNotes(): List<Note> {
        return repository.getNotes().map { Note(it.id, it.title, it.content) }
    }

    suspend fun addNote(note: Note) {
        val noteModel = NoteModel(note.id, note.title, note.content)
        repository.addNote(noteModel)
    }

    suspend fun deleteNote(note: Note) {
        val noteModel = NoteModel(note.id, note.title, note.content)
        repository.removeNote(noteModel)
    }
}