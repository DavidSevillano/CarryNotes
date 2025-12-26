package com.burixer.mynotesapp.data.manager

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.receiveAsFlow

object AchievementNotificationManager {
    private val _achievementQueue = Channel<String>(
        capacity = Channel.UNLIMITED
    )

    val achievementQueue = _achievementQueue.receiveAsFlow()

    suspend fun showNotification(name: String) {
        _achievementQueue.send(name)
    }
}