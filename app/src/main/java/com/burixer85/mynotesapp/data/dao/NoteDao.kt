package com.burixer85.mynotesapp.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.burixer85.mynotesapp.data.model.NoteModel

@Dao
interface NoteDao {
    @Query("SELECT * FROM notes")
    suspend fun getAllNotes(): List<NoteModel>

    @Insert
    suspend fun insertNote(note: NoteModel)

    @Delete
    suspend fun deleteNote(note: NoteModel)
}