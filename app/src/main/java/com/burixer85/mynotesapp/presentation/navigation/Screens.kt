package com.burixer85.mynotesapp.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
object QuickNotesRoute

@Serializable
object CategoriesRoute

@Serializable
data class NotesRoute(val categoryId: Int, val categoryName: String)