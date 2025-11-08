package com.burixer85.mynotesapp.presentation.model

import com.burixer85.mynotesapp.data.entity.CategoryEntity

data class Category(
    val id: Int = 0,
    val title: String,
    val notes: List<Note>
)

fun Category.toEntity() = CategoryEntity(
    id = id,
    title = title
)


