package com.burixer85.mynotesapp.domain.model

import com.burixer85.mynotesapp.data.entity.NoteModel

data class Note(
    val id: Int,
    val title: String,
    val content: String,
    val categoryId: Int
)

fun NoteModel.toDomain() = Note(id, title, content, categoryId)