package com.burixer.mynotesapp.data.manager

import com.burixer85.mynotesapp.data.database.AppDatabase

class AchievementManager(private val db: AppDatabase) {

    private suspend fun unlock(id: Int, name: String) {
        val achievementUnlocked = db.achievementDao().unlockAchievement(id)
        if (achievementUnlocked > 0) {
            AchievementNotificationManager.showNotification(name)
        }
    }

    suspend fun checkQuickNoteAchievements() {
        val count = db.quickNoteDao().getQuickNotesCount()

        if (count >= 1) unlock(1, "Primeros Pasos")
        if (count >= 5) unlock(2, "Pensamiento veloz")
        if (count >= 20) unlock(3, "Flash")
    }

    suspend fun checkCategoryAchievements() {
        val count = db.categoryDao().getCategoriesCount()

        if (count >= 1) unlock(4, "Organizador")
        if (count >= 5) unlock(5, "Maestro de categorías")
        if (count >= 10) unlock(6, "Arquitecto de ideas")
    }

    suspend fun checkNoteAchievements() {
        val count = db.noteDao().getTotalNotesCount()

        if (count >= 1) unlock(7, "Escritor")
        if (count >= 10) unlock(8, "Diario personal")
        if (count >= 50) unlock(9, "Autor prolífico")
    }

    suspend fun checkDeleteAchievement() {
        unlock(10, "Mente limpia")
    }

    suspend fun checkGlobalAchievements() {
        val unlockedCount = db.achievementDao().getAllAchievements().count { it.isUnlocked }

        if (unlockedCount >= 5) unlock(11, "Coleccionista")
        if (unlockedCount >= 11) unlock(12, "Maestro de logros")
    }
}