package com.burixer85.mynotesapp.data.converter

import androidx.room.TypeConverter
import com.burixer85.mynotesapp.data.entity.CategoryEntitiy

class CategoryModelConverter {
    @TypeConverter
    fun fromCategoryModel(category: CategoryEntitiy): String = category.title

    @TypeConverter
    fun toCategoryModel(title: String): CategoryEntitiy = CategoryEntitiy(title = title)
}