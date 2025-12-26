package com.burixer85.mynotesapp.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.burixer.mynotesapp.data.manager.AchievementNotificationManager
import com.burixer.mynotesapp.presentation.components.AchievementPopup
import com.burixer85.mynotesapp.R
import com.burixer85.mynotesapp.core.EventType
import com.burixer85.mynotesapp.core.ScreenEvent
import com.burixer85.mynotesapp.presentation.components.CarryFloatingActionButton
import com.burixer85.mynotesapp.presentation.components.CarryNavigationBar
import com.burixer85.mynotesapp.presentation.navigation.NavigationDestination
import com.burixer85.mynotesapp.presentation.navigation.NavigationHost
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun MainScreen() {

    val sharedViewModel: SharedViewModel = viewModel()
    val currentRoute by sharedViewModel.currentRoute.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    var unlockedAchievementName by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        AchievementNotificationManager.achievementQueue.collect { name ->
            unlockedAchievementName = name
            delay(4000) // Se muestra durante 4 segundos
            unlockedAchievementName = null
        }
    }

    LaunchedEffect(Unit) {
        sharedViewModel.eventFlow.collect { event ->

            val itemTypeString = when (event.type) {
                is EventType.QuickNote -> context.getString(R.string.event_type_quicknote)
                is EventType.Note -> context.getString(R.string.event_type_note)
                is EventType.Category -> context.getString(R.string.event_type_category)
            }

            val message = when (event) {
                is ScreenEvent.Created -> context.getString(R.string.snackbar_item_added, itemTypeString)
                is ScreenEvent.Updated -> context.getString(R.string.snackbar_item_updated, itemTypeString)
                is ScreenEvent.Deleted -> context.getString(R.string.snackbar_item_deleted, itemTypeString)
            }

            scope.launch {
                snackbarHostState.showSnackbar(
                    message = message,
                    duration = SnackbarDuration.Short
                )
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            contentWindowInsets = WindowInsets(0.dp, 0.dp, 0.dp, 0.dp),
            containerColor = Color(0xFF212121),
            snackbarHost = {
                SnackbarHost(hostState = snackbarHostState) { data ->
                    Snackbar(
                        snackbarData = data,
                        containerColor = Color(0xFF333333),
                        contentColor = Color.White,
                        actionColor = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(12.dp)
                    )
                }
            },
            bottomBar = {
                CarryNavigationBar(
                    currentDestination = currentRoute as NavigationDestination,
                    onItemClick = { destination ->
                        sharedViewModel.setCurrentRoute(destination)
                    }
                )
            },
            floatingActionButton = {
                CarryFloatingActionButton(
                    modifier = Modifier.padding(16.dp),
                    onOptionSelected = { option ->
                        sharedViewModel.onFabOptionSelected(option)
                    }
                )
            }    ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                Text(
                    modifier = Modifier.padding(top = 24.dp, start = 24.dp, end = 24.dp),
                    text = stringResource(R.string.Main_Screen_Text_Tittle),
                    color = Color.White,
                    style = MaterialTheme.typography.headlineSmall
                )
                Box(
                    Modifier
                        .fillMaxWidth()
                        .padding(24.dp)
                        .height(60.dp)
                        .border(
                            BorderStroke(2.dp, Color.White),
                            shape = RoundedCornerShape(32.dp)
                        )
                        .background(
                            color = Color(0xFF303030),
                            shape = RoundedCornerShape(32.dp)
                        )
                        .clickable {
                            sharedViewModel.setCurrentRoute(NavigationDestination.AchievementsNav)
                        },
                    contentAlignment = Alignment.Center
                ) {

                    Text(
                        text = stringResource(R.string.Main_Screen_Button_Achievements),
                        color = Color.White,
                        style = MaterialTheme.typography.labelLarge
                    )

                }
                Spacer(
                    modifier = Modifier.padding(12.dp),
                )
                NavigationHost(modifier = Modifier.weight(1f))
            }
        }
        AnimatedVisibility(
            visible = unlockedAchievementName != null,
            enter = slideInHorizontally(
                initialOffsetX = { fullWidth -> fullWidth }
            ) + fadeIn(
                animationSpec = tween(1000)
            ),
            exit = slideOutHorizontally(
                targetOffsetX = { fullWidth -> fullWidth }
            ) + fadeOut()
            ,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 40.dp)
        ) {

            var lastValidName by remember{ mutableStateOf("")}

            LaunchedEffect(unlockedAchievementName) {
                if (unlockedAchievementName != null) {
                    lastValidName = unlockedAchievementName!!
                }
            }
            AchievementPopup(name = lastValidName)
        }
    }

}