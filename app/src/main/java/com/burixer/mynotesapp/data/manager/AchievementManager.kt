package com.burixer.mynotesapp.data.manager

import com.burixer85.mynotesapp.data.database.AppDatabase

class AchievementManager(private val db: AppDatabase) {

    private suspend fun unlock(id: Int) {
        db.achievementDao().unlockAchievement(id)
    }

    suspend fun checkQuickNoteAchievements() {
        val count = db.quickNoteDao().getQuickNotesCount()

        if (count >= 1) unlock(1)
        if (count >= 5) unlock(2)
        if (count >= 20) unlock(3)
    }

    suspend fun checkCategoryAchievements() {
        val count = db.categoryDao().getCategoriesCount()

        if (count >= 1) unlock(4)
        if (count >= 5) unlock(5)
        if (count >= 10) unlock(6)
    }

    suspend fun checkNoteAchievements() {
        val count = db.noteDao().getTotalNotesCount()

        if (count >= 1) unlock(7)
        if (count >= 10) unlock(8)
        if (count >= 50) unlock(9)
    }

    suspend fun checkDeleteAchievement() {
        unlock(10)
    }

    suspend fun checkGlobalAchievements() {
        val unlockedCount = db.achievementDao().getAllAchievements().count { it.isUnlocked }

        if (unlockedCount >= 5) unlock(11)
        if (unlockedCount >= 11) unlock(12)
    }
}