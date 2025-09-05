package com.burixer85.mynotesapp.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.burixer85.mynotesapp.R
import com.burixer85.mynotesapp.presentation.model.Category
import com.burixer85.mynotesapp.presentation.model.Note

@Composable
fun MainScreen() {
    val categoryCumple = Category("Cumpleaños")
    val categoryRecetas = Category("Recetas")

    val notes: List<Note> = listOf(
        Note("1", categoryCumple, "Contenido de la nota 1 (cumple 1)"),
        Note("2", categoryCumple, "Contenido de la nota 2 (cumple 2)"),
        Note("3", categoryRecetas, "Contenido de la nota 3 (Receta1)")
    )

    Scaffold { padding ->
        Column(Modifier.padding(padding)) {
            Text(modifier = Modifier.padding(8.dp),text = stringResource(R.string.Main_Screen_Text_Tittle))
            Button(modifier = Modifier.padding(8.dp), onClick = {}) {
                Text(text = stringResource(R.string.Main_Screen_Button_Achievements))
            }
            Text(modifier = Modifier.padding(8.dp), text = stringResource(R.string.Main_Screen_Text_Quick_Notes))
            Text(modifier = Modifier.padding(8.dp), text = stringResource(R.string.Main_Screen_Text_Categories))

        }
    }
}