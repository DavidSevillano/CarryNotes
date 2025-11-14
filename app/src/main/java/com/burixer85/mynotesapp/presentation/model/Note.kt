package com.burixer85.mynotesapp.presentation.model

import com.burixer85.mynotesapp.data.entity.NoteEntity

data class Note(
    val id: Int = 0,
    override val title: String,
    override val content: String
) : DisplayableNote

fun Note.toEntity(categoryId: Int) = NoteEntity(
    id = id,
    title = title,
    content = content,
    categoryId = categoryId
)