package com.burixer85.mynotesapp.data.entity

import androidx.room.Embedded
import androidx.room.Relation

data class CategoryWithNotes(
    @Embedded val category: CategoryEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "categoryId"
    )
    val notes: List<NoteEntity>
)