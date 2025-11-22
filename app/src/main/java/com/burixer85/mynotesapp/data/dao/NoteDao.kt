package com.burixer85.mynotesapp.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.burixer85.mynotesapp.data.entity.NoteEntity

@Dao
interface NoteDao {

    @Query("SELECT * FROM notes WHERE categoryId = :categoryId")
    suspend fun getNotesByCategoryId(categoryId: Int): List<NoteEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNote(note: NoteEntity)

    @Update
    suspend fun updateNote(note: NoteEntity)

    @Delete
    suspend fun deleteNote(note: NoteEntity)
}