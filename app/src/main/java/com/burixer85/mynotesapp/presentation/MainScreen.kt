package com.burixer85.mynotesapp.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.burixer85.mynotesapp.R
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFF212121))
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
                        shape = RoundedCornerShape(14.dp)
                    ),
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
            content(PaddingValues())
        }

    }
}