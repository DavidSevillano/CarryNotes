package com.burixer85.mynotesapp.presentation

import android.R.style
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.burixer85.mynotesapp.R
import com.burixer85.mynotesapp.presentation.components.CarryAllCategories
import com.burixer85.mynotesapp.presentation.components.CarryAllQuickNotes
import com.burixer85.mynotesapp.presentation.model.Category
import com.burixer85.mynotesapp.presentation.model.Note
import com.burixer85.mynotesapp.presentation.model.QuickNote

@Composable
fun MainScreen(mainScreenViewModel: MainScreenViewModel = viewModel()) {
    val uiState by mainScreenViewModel.uiState.collectAsStateWithLifecycle()

    val notesCumple: List<Note> = listOf(
        Note(1, "1", "Contenido de la nota 1 (cumple 1)"),
        Note(2, "2", "Contenido de la nota 2 (cumple 2)"),
        Note(3, "3", "Contenido de la nota 3 (cumple 3)")
    )

    val notesRecetas: List<Note> = listOf(
        Note(1, "1", "Contenido de la nota 1 (receta 1)"),
        Note(2, "2", "Contenido de la nota 2 (receta 2)"),
    )

    val categoryCumple = Category(1, "Cumpleaños", notesCumple)
    val categoryRecetas = Category(2, "Recetas", notesRecetas)

    val categories: List<Category> = listOf(
        categoryCumple,
        categoryRecetas,
        categoryCumple,
        categoryRecetas,
        categoryCumple,
        categoryRecetas,
        categoryCumple,
        categoryRecetas
    )

    val quickNotes: List<QuickNote> = listOf(
        QuickNote("Código puzzle", "Contenido de la nota rapida 1"),
        QuickNote("Segundos restantes", "Contenido de la nota rapida 2"),
        QuickNote("Nombre amigo", "Contenido de la nota rapida 3")

    )


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets(0, 0, 0, 0), //Permite que no se transforme al minimizarla
        containerColor = Color(0xFF212121)
    ) { padding ->
        Column(Modifier.padding(padding)) {
            Text(
                modifier = Modifier.padding(16.dp),
                text = stringResource(R.string.Main_Screen_Text_Tittle),
                color = Color.White,
                style = MaterialTheme.typography.headlineSmall
            )
            Button(modifier = Modifier.padding(16.dp), onClick = {}) {
                Text(
                    text = stringResource(R.string.Main_Screen_Button_Achievements),
                    color = Color.White,
                    style = MaterialTheme.typography.labelLarge
                )
            }
            Text(
                modifier = Modifier.padding(16.dp),
                text = stringResource(R.string.Main_Screen_Text_Quick_Notes),
                color = Color.White,
                style = MaterialTheme.typography.titleSmall
            )
            if (quickNotes.isNotEmpty()) {
                CarryAllQuickNotes(uiState.quickNotes)
            }
            Text(
                modifier = Modifier.padding(16.dp),
                text = stringResource(R.string.Main_Screen_Text_Categories),
                color = Color.White,
                style = MaterialTheme.typography.titleSmall
            )
            if (categories.isNotEmpty()) {
                CarryAllCategories(uiState.categories)
            }
        }
    }
}