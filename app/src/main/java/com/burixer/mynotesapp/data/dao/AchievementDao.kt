package com.burixer.mynotesapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.burixer.mynotesapp.data.entity.AchievementEntity

@Dao
interface AchievementDao {
    @Query("SELECT * FROM achievements ORDER BY isUnlocked = 1 DESC, id ASC")
    suspend fun getAllAchievements(): List<AchievementEntity>

    @Query("UPDATE achievements SET isUnlocked = 1 WHERE id = :id AND isUnlocked = 0")
    suspend fun unlockAchievement(id: Int): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAchievements(achievements: List<AchievementEntity>)
}