package com.burixer.mynotesapp.data.manager

import com.burixer85.mynotesapp.data.database.AppDatabase

class AchievementManager(private val db: AppDatabase) {

    private suspend fun unlock(id: Int, key: String) {
        val achievementUnlocked = db.achievementDao().unlockAchievement(id)
        if (achievementUnlocked > 0) {
            AchievementNotificationManager.showNotification(key)
        }
    }

    suspend fun checkQuickNoteAchievements() {
        val count = db.quickNoteDao().getQuickNotesCount()

        if (count >= 1) unlock(1, "Achievements_title_1")
        if (count >= 5) unlock(2, "Achievements_title_2")
        if (count >= 20) unlock(3, "Achievements_title_3")
    }

    suspend fun checkCategoryAchievements() {
        val count = db.categoryDao().getCategoriesCount()

        if (count >= 1) unlock(4, "Achievements_title_4")
        if (count >= 5) unlock(5, "Achievements_title_5")
        if (count >= 10) unlock(6, "Achievements_title_6")
    }

    suspend fun checkNoteAchievements() {
        val count = db.noteDao().getTotalNotesCount()

        if (count >= 1) unlock(7, "Achievements_title_7")
        if (count >= 10) unlock(8, "Achievements_title_8")
        if (count >= 50) unlock(9, "Achievements_title_9")
    }

    suspend fun checkDeleteAchievement() {
        unlock(10, "Achievements_title_10")
    }

    suspend fun checkGlobalAchievements() {
        val unlockedCount = db.achievementDao().getAllAchievements().count { it.isUnlocked }

        if (unlockedCount >= 5) unlock(11, "Achievements_title_11")
        if (unlockedCount >= 11) unlock(12, "Achievements_title_12")
    }
}