package com.burixer85.mynotesapp.presentation.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

sealed class NavigationDestination : NavKey {
    @Serializable
    data object QuickNotesNav : NavigationDestination()
    @Serializable
    data object CategoriesNav : NavigationDestination()
    @Serializable
    data class NotesNav (val categoryId: Int, val categoryName: String) : NavigationDestination()
    @Serializable
    data object ErrorNav : NavigationDestination()
}