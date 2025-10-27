package com.burixer85.mynotesapp.presentation.model

import com.burixer85.mynotesapp.data.entity.QuickNoteEntity

data class QuickNote(
    val title: String,
    val content: String
)

fun QuickNote.toEntity() = QuickNoteEntity(
    title = title,
    content = content
)


