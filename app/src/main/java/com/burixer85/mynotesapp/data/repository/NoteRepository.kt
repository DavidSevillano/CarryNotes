package com.burixer85.mynotesapp.data.repository

import com.burixer85.mynotesapp.data.dao.NoteDao
import com.burixer85.mynotesapp.data.entity.toDatabase
import com.burixer85.mynotesapp.domain.model.Note
import com.burixer85.mynotesapp.domain.model.toDomain
import com.burixer85.mynotesapp.domain.repository.INoteRepository

class NoteRepository(private val noteDao: NoteDao) : INoteRepository {
    override suspend fun getNotes(): List<Note> {
        val response = noteDao.getAllNotes()
        return response.map { it.toDomain() }
    }

    override suspend fun addNote(note: Note) {
        noteDao.insertNote(note.toDatabase())
    }

    override suspend fun removeNote(note: Note) {
        noteDao.deleteNote(note.toDatabase())
    }
}
