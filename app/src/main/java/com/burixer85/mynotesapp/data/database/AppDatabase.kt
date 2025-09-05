package com.burixer85.mynotesapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.burixer85.mynotesapp.data.dao.NoteDao
import com.burixer85.mynotesapp.data.model.NoteModel

@Database(entities = [NoteModel::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}