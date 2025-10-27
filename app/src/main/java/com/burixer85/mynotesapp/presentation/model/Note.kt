package com.burixer85.mynotesapp.presentation.model

import com.burixer85.mynotesapp.data.entity.NoteEntity

data class Note(
    val id: Int,
    val title: String,
    val content: String
)

fun Note.toEntity(categoryId: Int) = NoteEntity(
    id = id,
    title = title,
    content = content,
    categoryId = categoryId
)