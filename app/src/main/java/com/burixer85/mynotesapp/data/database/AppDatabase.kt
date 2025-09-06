package com.burixer85.mynotesapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.burixer85.mynotesapp.data.converter.CategoryModelConverter
import com.burixer85.mynotesapp.data.converter.QuickNoteModelConverter
import com.burixer85.mynotesapp.data.dao.NoteDao
import com.burixer85.mynotesapp.data.model.NoteModel

@Database(entities = [NoteModel::class], version = 2)
@TypeConverters(CategoryModelConverter::class, QuickNoteModelConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}