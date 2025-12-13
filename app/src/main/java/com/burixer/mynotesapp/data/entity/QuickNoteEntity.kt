package com.burixer85.mynotesapp.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.burixer85.mynotesapp.presentation.model.QuickNote

@Entity(tableName = "quick_notes")
data class QuickNoteEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "content") val content: String,
    @ColumnInfo(name = "createdAt") val createdAt: Long = System.currentTimeMillis()
)

fun QuickNoteEntity.toPresentation(): QuickNote {
    return QuickNote(
        id = this.id,
        title = this.title,
        content = this.content,
        createdAt = this.createdAt
    )
}
