package com.burixer85.mynotesapp.presentation.model

import com.burixer85.mynotesapp.data.entity.CategoryEntity

data class Category(
    val id: Int = 0,
    val name: String,
    val notes: List<Note>,
    val createdAt: Long = System.currentTimeMillis()
)

fun Category.toEntity() = CategoryEntity(
    id = id,
    name = name,
    createdAt = createdAt
)


