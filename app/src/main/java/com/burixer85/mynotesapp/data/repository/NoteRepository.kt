package com.burixer85.mynotesapp.data.repository

import com.burixer85.mynotesapp.data.dao.NoteDao
import com.burixer85.mynotesapp.data.model.NoteModel
import com.burixer85.mynotesapp.domain.repository.INoteRepository

class NoteRepository(private val noteDao: NoteDao) : INoteRepository {
    override suspend fun getNotes() = noteDao.getAllNotes()
    override suspend fun addNote(note: NoteModel) = noteDao.insertNote(note)
    override suspend fun removeNote(note: NoteModel) = noteDao.deleteNote(note)
}