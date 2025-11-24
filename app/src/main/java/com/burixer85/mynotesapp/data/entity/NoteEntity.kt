package com.burixer85.mynotesapp.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.burixer85.mynotesapp.presentation.model.Note

@Entity(tableName = "notes",
    foreignKeys = [
        ForeignKey(
            entity = CategoryEntity::class,
            parentColumns = ["id"],
            childColumns = ["categoryId"],
            onDelete = ForeignKey.CASCADE
        )
    ])
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
        content = this.content,
        categoryId = this.categoryId
    )
}
