package com.burixer.mynotesapp.presentation.Screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.burixer.mynotesapp.data.entity.toPresentation
import com.burixer.mynotesapp.presentation.model.Achievement
import com.burixer85.mynotesapp.data.application.RoomApplication
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AchievementsScreenViewModel : ViewModel (){
    private val _uiState = MutableStateFlow(AchievementsScreenUI())
    val uiState: StateFlow<AchievementsScreenUI> = _uiState

    init {
        loadAchievements()
    }

    fun loadAchievements() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update { it.copy(isLoading = true) }

            val achievementsFromDb = RoomApplication.db.achievementDao().getAllAchievements()

            val achievementsList = achievementsFromDb.map { it.toPresentation() }

            _uiState.update {
                it.copy(
                    achievements = achievementsList,
                    isLoading = false
                )
            }
        }
    }
}


data class AchievementsScreenUI(
    val achievements: List<Achievement> = emptyList(),
    val isLoading: Boolean = false,
)