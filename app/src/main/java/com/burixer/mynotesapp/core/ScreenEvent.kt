package com.burixer85.mynotesapp.core

sealed class EventType {
    data object QuickNote : EventType()
    data object Note : EventType()
    data object Category : EventType()
}

sealed class ScreenEvent(val type: EventType) {
    class Deleted(type: EventType) : ScreenEvent(type)
    class Updated(type: EventType) : ScreenEvent(type)
    class Created(type: EventType) : ScreenEvent(type)
}