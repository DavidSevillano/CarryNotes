package com.burixer85.mynotesapp.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.burixer85.mynotesapp.presentation.model.Note

@Entity(tableName = "notes")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "content") val content: String,
    @ColumnInfo(name = "categoryId") val categoryId: Int
)

fun NoteEntity.toPresentation(): Note {
    return Note(
        id = this.id,
        title = this.title,
        content = this.content
    )
}
