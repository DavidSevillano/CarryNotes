package com.burixer85.mynotesapp.core.ex

import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey

fun NavBackStack<NavKey>.navigateTo(screen: NavKey){
    add(screen)
}