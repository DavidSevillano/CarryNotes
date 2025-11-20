package com.burixer85.mynotesapp.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.burixer85.mynotesapp.data.entity.CategoryEntity
import com.burixer85.mynotesapp.data.entity.CategoryWithNotes
import com.burixer85.mynotesapp.data.entity.QuickNoteEntity

@Dao
interface CategoryDao {
    @Transaction
    @Query("SELECT * FROM categories")
    suspend fun getAllCategories(): List<CategoryWithNotes>

    @Transaction
    @Query("SELECT * FROM categories WHERE id = :categoryId")
    suspend fun getCategoryById(categoryId: Int): CategoryWithNotes?


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCategory(category: CategoryEntity)

    @Update
    suspend fun updateCategory(category: CategoryEntity)

    @Delete
    suspend fun deleteCategory(category: CategoryEntity)


}