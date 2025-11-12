package com.burixer85.mynotesapp.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.burixer85.mynotesapp.data.entity.CategoryEntity
import com.burixer85.mynotesapp.data.entity.CategoryWithNotes

@Dao
interface CategoryDao {
    @Transaction
    @Query("SELECT * FROM categories")
    suspend fun getAllCategories(): List<CategoryWithNotes>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCategory(category: CategoryEntity)

    @Delete
    suspend fun deleteCategory(category: CategoryEntity)


}