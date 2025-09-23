package com.burixer85.mynotesapp.domain.usecase

import com.burixer85.mynotesapp.data.entity.NoteModel
import com.burixer85.mynotesapp.data.entity.toDatabase
import com.burixer85.mynotesapp.domain.model.Note
import com.burixer85.mynotesapp.domain.repository.INoteRepository

class NotesUseCase(private val repository: INoteRepository) {

        suspend fun getNotes(): List<Note> {
            return repository.getNotes()
        }

        suspend fun addNote(note: Note) {
            repository.addNote(note)
        }

        suspend fun deleteNote(note: Note) {
            repository.removeNote(note)
        }
    }