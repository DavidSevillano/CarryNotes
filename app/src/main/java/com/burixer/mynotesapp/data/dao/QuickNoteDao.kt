package com.burixer85.mynotesapp.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.burixer85.mynotesapp.data.entity.QuickNoteEntity

@Dao
interface QuickNoteDao {
    @Query("SELECT * FROM quick_notes ORDER BY createdAt DESC")
    suspend fun getAllQuickNotes(): List<QuickNoteEntity>

    @Query("SELECT COUNT(*) FROM quick_notes")
    suspend fun getQuickNotesCount(): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertQuickNote(quickNote: QuickNoteEntity)

    @Update
    suspend fun updateQuickNote(quickNote: QuickNoteEntity)

    @Delete
    suspend fun deleteQuickNote(quickNote: QuickNoteEntity)
}