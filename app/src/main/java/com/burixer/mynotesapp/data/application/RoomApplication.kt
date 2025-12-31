package com.burixer85.mynotesapp.data.application

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.activity.result.launch
import androidx.compose.ui.res.stringResource
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.burixer.mynotesapp.data.entity.AchievementEntity
import com.burixer85.mynotesapp.R
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
                    "carry_notes_database_v2"
                )
                    .addCallback(AppDatabaseCallback(context.applicationContext, scope))
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
        private val context: Context,
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

                            AchievementEntity(
                                id = 1,
                                title = R.string.Achievements_title_1,
                                description = R.string.Achievements_description_1,
                                isUnlocked = false
                            ),
                            AchievementEntity(
                                id = 2,
                                title = R.string.Achievements_title_2,
                                description = R.string.Achievements_description_2,
                                isUnlocked = false
                            ),
                            AchievementEntity(
                                id = 3,
                                title = R.string.Achievements_title_3,
                                description = R.string.Achievements_description_3,
                                isUnlocked = false
                            ),
                            AchievementEntity(
                                id = 4,
                                title = R.string.Achievements_title_4,
                                description = R.string.Achievements_description_4,
                                isUnlocked = false
                            ),
                            AchievementEntity(
                                id = 5,
                                title = R.string.Achievements_title_5,
                                description = R.string.Achievements_description_5,
                                isUnlocked = false
                            ),
                            AchievementEntity(
                                id = 6,
                                title = R.string.Achievements_title_6,
                                description = R.string.Achievements_description_6,
                                isUnlocked = false
                            ),
                            AchievementEntity(
                                id = 7,
                                title = R.string.Achievements_title_7,
                                description = R.string.Achievements_description_7,
                                isUnlocked = false
                            ),
                            AchievementEntity(
                                id = 8,
                                title = R.string.Achievements_title_8,
                                description = R.string.Achievements_description_8,
                                isUnlocked = false
                            ),
                            AchievementEntity(
                                id = 9,
                                title = R.string.Achievements_title_9,
                                description = R.string.Achievements_description_9,
                                isUnlocked = false
                            ),
                            AchievementEntity(
                                id = 10,
                                title = R.string.Achievements_title_10,
                                description = R.string.Achievements_description_10,
                                isUnlocked = false
                            ),
                            AchievementEntity(
                                id = 11,
                                title = R.string.Achievements_title_11,
                                description = R.string.Achievements_description_11,
                                isUnlocked = false
                            ),
                            AchievementEntity(
                                id = 12,
                                title = R.string.Achievements_title_12,
                                description = R.string.Achievements_description_12,
                                isUnlocked = false
                            )
                        )
                        dao.insertAchievements(initial)
                    }
                }
            }
        }
    }
}