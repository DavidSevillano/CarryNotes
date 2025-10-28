package com.burixer85.mynotesapp.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.burixer85.mynotesapp.presentation.model.Category

@Entity(tableName = "categories")
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "title") val title: String,
)

fun CategoryEntity.toPresentation(): Category {
    return Category(
        id = this.id,
        title = this.title,
        notes = emptyList()
    )
}
