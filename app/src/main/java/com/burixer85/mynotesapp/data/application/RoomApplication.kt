package com.burixer85.mynotesapp.data.application

import android.app.Application
import androidx.room.Room
import com.burixer85.mynotesapp.data.database.AppDatabase

class RoomApplication : Application() {
    companion object {
        lateinit var db: AppDatabase
    }

    override fun onCreate() {
        super.onCreate()

        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "app_database"
        ).fallbackToDestructiveMigration()
            .build(
            )
    }
}