package com.burixer85.mynotesapp.domain.model

data class Category(
    val id: Int,
    val title: String,
    val notes: List<Note>
)