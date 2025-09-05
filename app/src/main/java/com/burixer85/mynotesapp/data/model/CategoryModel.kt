package com.burixer85.mynotesapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category")
data class CategoryModel(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
)
