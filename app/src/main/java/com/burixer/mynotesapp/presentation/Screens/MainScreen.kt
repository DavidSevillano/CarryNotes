package com.burixer85.mynotesapp.presentation

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.core.os.LocaleListCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.burixer.mynotesapp.core.utilities.getTranslatedAchievement
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Language
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon

@Composable
fun MainScreen() {

    val sharedViewModel: SharedViewModel = viewModel()
    val currentRoute by sharedViewModel.currentRoute.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    var unlockedAchievementName by remember { mutableStateOf<String?>(null) }

    var expanded by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        AchievementNotificationManager.achievementQueue.collect { name ->
            try {
                val mediaPlayer = MediaPlayer.create(context, R.raw.achievement_ding)
                mediaPlayer.setOnCompletionListener { it.release() }
                mediaPlayer.start()
            } catch (e: Exception) {
                e.printStackTrace()
            }

            unlockedAchievementName = name

            delay(4000)
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
                is ScreenEvent.Created -> context.getString(
                    R.string.snackbar_item_added,
                    itemTypeString
                )

                is ScreenEvent.Updated -> context.getString(
                    R.string.snackbar_item_updated,
                    itemTypeString
                )

                is ScreenEvent.Deleted -> context.getString(
                    R.string.snackbar_item_deleted,
                    itemTypeString
                )
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
            }) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 36.dp, start = 24.dp, end = 24.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = stringResource(R.string.Main_Screen_Text_Tittle),
                        color = Color.White,
                        style = MaterialTheme.typography.headlineSmall
                    )

                    Box {
                        Icon(
                            imageVector = Icons.Default.Language,
                            contentDescription = "Cambiar idioma",
                            tint = Color.White,
                            modifier = Modifier
                                .size(50.dp)
                                .clickable { expanded = true }
                                .padding(8.dp)
                        )

                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false },
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier
                                .background(Color(0xFF333333))
                                .border(
                                    0.5.dp,
                                    Color.White.copy(alpha = 0.2f),
                                    RoundedCornerShape(16.dp)
                                )
                        ) {
                            DropdownMenuItem(
                                text = {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(stringResource(R.string.Main_Screen_Icon_Language_Spanish), style = MaterialTheme.typography.titleMedium)
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(stringResource(R.string.Main_Screen_Text_Language_Spanish), color = Color.White)
                                    }
                                },
                                onClick = {
                                    expanded = false
                                    scope.launch {
                                        delay(150)
                                        val appLocale: LocaleListCompat = LocaleListCompat.forLanguageTags("es")
                                        AppCompatDelegate.setApplicationLocales(appLocale)
                                    }
                                }
                            )

                            DropdownMenuItem(
                                text = {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(stringResource(R.string.Main_Screen_Icon_Language_English), style = MaterialTheme.typography.titleMedium)
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(stringResource(R.string.Main_Screen_Text_Language_English), color = Color.White)
                                    }
                                },
                                onClick = {
                                    expanded = false
                                    scope.launch {
                                        delay(150)
                                        val appLocale: LocaleListCompat = LocaleListCompat.forLanguageTags("en")
                                        AppCompatDelegate.setApplicationLocales(appLocale)
                                    }
                                }
                            )
                        }
                    }
                }
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
            ),
            exit = slideOutHorizontally(
                targetOffsetX = { fullWidth -> fullWidth }
            ),
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 40.dp)
        ) {

            var lastValidName by remember { mutableStateOf("") }

            LaunchedEffect(unlockedAchievementName) {
                if (unlockedAchievementName != null) {
                    lastValidName = unlockedAchievementName!!
                }
            }
            AchievementPopup(name = getTranslatedAchievement(lastValidName))
        }
    }

}