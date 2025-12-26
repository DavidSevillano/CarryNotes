package com.burixer.mynotesapp.presentation.model

import com.burixer.mynotesapp.data.entity.AchievementEntity

data class Achievement(
    val id: Int,
    val title: Int,
    val description: Int,
    val isUnlocked: Boolean = false
)

fun Achievement.toEntity() = AchievementEntity(
    id = id,
    title = title,
    description = description,
    isUnlocked = isUnlocked
)


