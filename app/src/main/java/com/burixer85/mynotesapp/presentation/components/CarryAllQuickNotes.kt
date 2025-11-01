package com.burixer85.mynotesapp.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.burixer85.mynotesapp.presentation.model.QuickNote
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment

@Composable
fun CarryAllQuickNotes(quickNotes: List<QuickNote>) {
    Column(modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "${quickNotes.size} notas rápidas",
            color = Color.White,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .padding(bottom = 32.dp)
        )
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            val chunkedNotes = quickNotes.chunked(2)

            items(chunkedNotes) { pairOfNotes ->
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    pairOfNotes.forEach { quickNote ->
                        CarryQuickNotes(title = quickNote.title)
                    }
                }
            }
        }
    }
}