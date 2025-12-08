package com.burixer85.mynotesapp.presentation.model

import com.burixer85.mynotesapp.data.entity.QuickNoteEntity

data class QuickNote(
    val id: Int = 0,
    override val title: String,
    override val content: String,
    val createdAt: Long = System.currentTimeMillis()
) : DisplayableNote

fun QuickNote.toEntity() = QuickNoteEntity(
    id = id,
    title = title,
    content = content,
    createdAt = createdAt
)


