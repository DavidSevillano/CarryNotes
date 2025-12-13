package com.burixer85.mynotesapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.burixer85.mynotesapp.data.dao.CategoryDao
import com.burixer85.mynotesapp.data.dao.NoteDao
import com.burixer85.mynotesapp.data.dao.QuickNoteDao
import com.burixer85.mynotesapp.data.entity.CategoryEntity
import com.burixer85.mynotesapp.data.entity.NoteEntity
import com.burixer85.mynotesapp.data.entity.QuickNoteEntity

@Database(entities = [NoteEntity::class, QuickNoteEntity::class, CategoryEntity::class],
    version = 3, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
    abstract fun quickNoteDao(): QuickNoteDao
    abstract fun categoryDao(): CategoryDao
}