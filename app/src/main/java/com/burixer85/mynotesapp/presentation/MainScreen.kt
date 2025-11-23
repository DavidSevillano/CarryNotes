package com.burixer85.mynotesapp.presentation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.burixer85.mynotesapp.presentation.components.CarryFloatingActionButton
import com.burixer85.mynotesapp.presentation.components.CarryNavigationBar

@Composable
fun MainScreen(
    navController: NavHostController,
    onItemClick: (route: Any) -> Unit,
    onFabOptionSelected: (String) -> Unit,
    content: @Composable (padding: PaddingValues) -> Unit
) {

    Scaffold(
        bottomBar = {
            CarryNavigationBar(
                navController = navController,
                onItemClick = onItemClick
            )
        },
        floatingActionButton = {
            CarryFloatingActionButton(
                modifier = Modifier.padding(16.dp),
                onOptionSelected = onFabOptionSelected
            )
        }    ) { innerPadding ->
        content(innerPadding)
    }
}