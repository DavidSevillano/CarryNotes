package com.burixer85.mynotesapp.data.database

import android.content.Context
import android.util.Log
import androidx.activity.addCallback
import androidx.activity.result.launch
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.burixer.mynotesapp.data.dao.AchievementDao
import com.burixer.mynotesapp.data.entity.AchievementEntity
import com.burixer85.mynotesapp.data.dao.CategoryDao
import com.burixer85.mynotesapp.data.dao.NoteDao
import com.burixer85.mynotesapp.data.dao.QuickNoteDao
import com.burixer85.mynotesapp.data.entity.CategoryEntity
import com.burixer85.mynotesapp.data.entity.NoteEntity
import com.burixer85.mynotesapp.data.entity.QuickNoteEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [NoteEntity::class, QuickNoteEntity::class, CategoryEntity::class, AchievementEntity::class],
    version = 5, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
    abstract fun quickNoteDao(): QuickNoteDao
    abstract fun categoryDao(): CategoryDao
    abstract fun achievementDao(): AchievementDao
}
