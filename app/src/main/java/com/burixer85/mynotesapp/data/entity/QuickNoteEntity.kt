package com.burixer85.mynotesapp.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.burixer85.mynotesapp.presentation.model.QuickNote

@Entity(tableName = "quick_notes")
data class QuickNoteEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val content: String
)

fun QuickNoteEntity.toPresentation(): QuickNote {
    return QuickNote(
        title = this.title,
        content = this.content
    )
}
