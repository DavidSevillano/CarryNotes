package com.burixer85.mynotesapp.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.burixer85.mynotesapp.data.entity.QuickNoteEntity

@Dao
interface QuickNoteDao {
    @Query("SELECT * FROM quick_notes")
    suspend fun getAllQuickNotes(): List<QuickNoteEntity>

    @Insert
    suspend fun insertQuickNote(quickNote: QuickNoteEntity)

    @Delete
    suspend fun deleteQuickNote(quickNote: QuickNoteEntity)
}