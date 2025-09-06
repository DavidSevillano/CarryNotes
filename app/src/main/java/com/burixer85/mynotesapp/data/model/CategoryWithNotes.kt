package com.burixer85.mynotesapp.data.model

import androidx.room.Embedded
import androidx.room.Relation

data class CategoryWithNotes(
    @Embedded val category: CategoryModel,
    @Relation(
        parentColumn = "id",
        entityColumn = "categoryId"
    )
    val notes: List<NoteModel>
)