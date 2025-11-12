package com.burixer85.mynotesapp.data.entity

import androidx.room.Embedded
import androidx.room.Relation
import com.burixer85.mynotesapp.presentation.model.Category

data class CategoryWithNotes(
    @Embedded
    val category: CategoryEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "categoryId"
    )
    val notes: List<NoteEntity>
)

fun CategoryWithNotes.toPresentation(): Category {
    return Category(
        id = this.category.id,
        title = this.category.title,
        notes = this.notes.map { noteEntity ->
            noteEntity.toPresentation()
        }
    )
}