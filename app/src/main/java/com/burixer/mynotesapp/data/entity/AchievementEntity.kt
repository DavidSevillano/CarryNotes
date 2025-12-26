package com.burixer.mynotesapp.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.burixer.mynotesapp.presentation.model.Achievement

@Entity(tableName = "achievements")
data class AchievementEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "title") val title: Int,
    @ColumnInfo(name = "description") val description: Int,
    @ColumnInfo(name = "isUnlocked") val isUnlocked: Boolean = false
)

fun AchievementEntity.toPresentation() = Achievement(
    id = id,
    title = title,
    description = description,
    isUnlocked = isUnlocked
)


