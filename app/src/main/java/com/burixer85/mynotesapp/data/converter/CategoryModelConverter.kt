package com.burixer85.mynotesapp.data.converter

import androidx.room.TypeConverter
import com.burixer85.mynotesapp.data.model.CategoryModel

class CategoryModelConverter {
    @TypeConverter
    fun fromCategoryModel(category: CategoryModel): String = category.title

    @TypeConverter
    fun toCategoryModel(title: String): CategoryModel = CategoryModel(title = title)
}