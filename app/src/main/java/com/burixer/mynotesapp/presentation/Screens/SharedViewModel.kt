package com.burixer85.mynotesapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.burixer85.mynotesapp.core.ScreenEvent
import com.burixer85.mynotesapp.presentation.navigation.NavigationDestination
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class SharedViewModel : ViewModel() {

    private val _currentRoute = MutableStateFlow<NavigationDestination>(NavigationDestination.QuickNotesNav)
    val currentRoute = _currentRoute.asStateFlow()

    private var _isFromNavHost = false

    private val _eventChannel = Channel<ScreenEvent>()
    val eventFlow = _eventChannel.receiveAsFlow()

    fun postEvent(event: ScreenEvent) {
        viewModelScope.launch {
            _eventChannel.send(event)
        }
    }

    fun setCurrentRoute(route: NavigationDestination, fromNavHost: Boolean = false) {
        if (fromNavHost) {
            _isFromNavHost = true
            if (_currentRoute.value != route) {
                _currentRoute.value = route
            }
        } else {
            _isFromNavHost = false
            _currentRoute.value = route
        }
    }

    fun isNavigationFromNavHost(): Boolean {
        val fromNavHost = _isFromNavHost
        if (fromNavHost) {
            _isFromNavHost = false
        }
        return fromNavHost
    }

    private val _fabAction = MutableStateFlow<String?>(null)
    val fabAction = _fabAction.asStateFlow()

    fun onFabOptionSelected(option: String) {
        _fabAction.value = option
    }

    fun onFabActionConsumed() {
        _fabAction.value = null
    }

    private val _dataChanged = MutableSharedFlow<Unit>(replay = 1)
    val dataChanged = _dataChanged.asSharedFlow()

    fun notifyDataChanged() {
        viewModelScope.launch {
            _dataChanged.emit(Unit)
        }
    }
}