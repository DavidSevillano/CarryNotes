package com.burixer85.mynotesapp

import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowInsetsController
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import com.burixer85.mynotesapp.presentation.MainScreen
import com.burixer85.mynotesapp.presentation.components.CarryNavigationBar
import com.burixer85.mynotesapp.presentation.navigation.NavigationWrapper
import com.burixer85.mynotesapp.ui.theme.MyNotesAppTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.decorView.post {
                window.insetsController?.let { controller ->
                    controller.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
                    controller.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
                }
            }
        }
        setContent {
            MyNotesAppTheme(darkTheme = true) {
                NavigationWrapper()
            }
        }
    }
}
