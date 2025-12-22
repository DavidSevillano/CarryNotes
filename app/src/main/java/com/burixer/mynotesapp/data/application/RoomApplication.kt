package com.burixer85.mynotesapp.data.application

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.activity.result.launch
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.burixer.mynotesapp.data.entity.AchievementEntity
import com.burixer85.mynotesapp.data.database.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class RoomApplication : Application() {

    companion object {
        lateinit var db: AppDatabase

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "carry_notes_database"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(AppDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

        db = getDatabase(this, applicationScope)
    }

    private class AppDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)

            INSTANCE?.let { database ->
                scope.launch(Dispatchers.IO) {
                    val dao = database.achievementDao()

                    val current = dao.getAllAchievements()
                    if (current.isEmpty()) {
                        val initial = listOf(

                            AchievementEntity(1, "Primeros pasos", "Crea tu primera nota rápida",  false),

                            AchievementEntity(2, "Pensamiento veloz", "Crea 5 notas rápidas",  false),

                            AchievementEntity(3, "Flash", "Crea 20 notas rápidas",  false),

                            AchievementEntity(4, "Organizador", "Crea tu primera categoría",  false),

                            AchievementEntity(5, "Maestro organizador", "Ten 5 categorías creadas",  false),

                            AchievementEntity(6, "Arquitecto de ideas", "Ten 10 categorías creadas",  false),

                            AchievementEntity(7, "Escritor", "Crea tu primera nota detallada",  false),

                            AchievementEntity(8, "Diario personal", "Crea 10 notas detalladas",  false),

                            AchievementEntity(9, "Autor prolífico", "Crea 50 notas detalladas",  false),

                            AchievementEntity(10, "Mente limpia", "Borra una nota por primera vez",  false),

                            AchievementEntity(11, "Seguridad ante todo", "Haz un backup de tus notas",  false),

                            AchievementEntity(12, "Coleccionista", "Desbloquea 5 logros",  false),

                            AchievementEntity(13, "Maestro de logros", "Desbloquea todos los logros",  false),

                        )
                        dao.insertAchievements(initial)
                    }
                }
            }
        }
    }
}