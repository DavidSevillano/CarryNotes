package com.burixer85.mynotesapp.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.burixer85.mynotesapp.R
import com.burixer85.mynotesapp.presentation.components.CarryAllCategories
import com.burixer85.mynotesapp.presentation.components.CarryAllQuickNotes
import kotlin.collections.chunked

@Composable
fun QuickNotesScreen(
    modifier: Modifier,
    quickNotesScreenViewModel: QuickNotesScreenViewModel = viewModel()
) {
    val uiState by quickNotesScreenViewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier,
        contentWindowInsets = WindowInsets(
            0,
            0,
            0,
            0
        ), //Permite que no se transforme al minimizarla
        containerColor = Color(0xFF212121)
    ) { padding ->
        Column(Modifier.padding(padding)) {
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
            if (uiState.quickNotes.isEmpty()) {
                CarryAllQuickNotes(uiState.quickNotes)
            } else {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Sin notas rápidas",
                        color = Color.White,
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier
                            .padding(bottom = 32.dp)
                    )

                    Box(
                        Modifier
                            .fillMaxWidth()
                            .height(240.dp)
                            .padding(12.dp)
                            .border(
                                BorderStroke(2.dp, Color.White),
                                shape = RoundedCornerShape(14.dp)
                            )
                            .background(
                                color = Color(0xFF303030),
                                shape = RoundedCornerShape(14.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {

                        Text(
                            text = "Añadir nota rápida",
                            color = Color.White,
                            style = MaterialTheme.typography.labelLarge,
                        )
                    }

                }
            }
        }
    }
}
