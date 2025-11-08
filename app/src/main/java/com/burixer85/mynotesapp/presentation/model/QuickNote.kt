package com.burixer85.mynotesapp.presentation.model

import com.burixer85.mynotesapp.data.entity.QuickNoteEntity

data class QuickNote(
    val id: Int = 0,
    val title: String,
    val content: String
)

fun QuickNote.toEntity() = QuickNoteEntity(
    id = id,
    title = title,
    content = content
)


