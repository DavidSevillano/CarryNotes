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

    @Query("UPDATE notes SET title = :title, content = :content, categoryId = :categoryId WHERE id = :id")
    suspend fun updateNote(id: Int, title: String, content: String, categoryId: Int)

    @Delete
    suspend fun deleteNote(note: NoteEntity)
}