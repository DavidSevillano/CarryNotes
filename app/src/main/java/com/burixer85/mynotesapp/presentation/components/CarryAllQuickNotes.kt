package com.burixer85.mynotesapp.presentation.components

import androidx.compose.foundation.BorderStroke
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
    Box(
        Modifier
            .height(160.dp)
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .border(
                BorderStroke(2.dp, Color.White),
                shape = RoundedCornerShape(46.dp)
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
        ) {
            Text(
                text = "${quickNotes.size} notas rápidas",
                color = Color.White,
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 8.dp, bottom = 16.dp)
            )
            Row(
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(quickNotes.take(3)) { quickNote ->
                        CarryQuickNotes(title = quickNote.title)
                    }
                }
            }
        }
    }
}