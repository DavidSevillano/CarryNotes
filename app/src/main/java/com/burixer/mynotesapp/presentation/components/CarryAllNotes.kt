package com.burixer85.mynotesapp.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import com.burixer85.mynotesapp.presentation.model.Note
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow

@Composable
fun CarryAllNotes(
    notes: List<Note>,
    categoryName: String,
    onNoteClick: (Note) -> Unit,
    onEditCategoryClick: () -> Unit,
    onAddNoteClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp, start = 16.dp, end = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(48.dp))

            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Editar nombre de categoría",
                        tint = Color.White,
                        modifier = Modifier
                            .size(30.dp)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null,
                                onClick = { onEditCategoryClick() }
                            )
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = categoryName.replaceFirstChar { if (it.isLowerCase()) it.uppercase() else it.toString() },
                        color = Color.White,
                        style = MaterialTheme.typography.titleLarge,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            if (notes.isNotEmpty()){
                IconButton(
                    onClick = { onAddNoteClick() },
                    modifier = Modifier
                        .size(48.dp)
                        .background(
                            color = Color(0xFF303030),
                            shape = CircleShape
                        )
                        .border(
                            width = 1.dp,
                            color = Color.White,
                            shape = CircleShape
                        )
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Añadir Nota",
                        tint = Color.White,
                        modifier = Modifier.size(32.dp)
                    )
                }
            } else {
                Spacer(modifier = Modifier.width(48.dp))
            }
        }

        if (notes.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(notes.chunked(2)) { pair ->
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        pair.forEach { note ->
                            Box(
                                Modifier
                                    .height(120.dp)
                                    .weight(1f)
                                    .border(
                                        BorderStroke(2.dp, Color.White),
                                        shape = RoundedCornerShape(14.dp)
                                    )
                                    .background(
                                        color = Color(0xFF303030),
                                        shape = RoundedCornerShape(14.dp)
                                    )
                                    .clickable { onNoteClick(note) }
                            ) {
                                Column(
                                    modifier = Modifier
                                        .align(Alignment.Center)
                                        .padding(8.dp)
                                ) {
                                    if (note.title.isNotEmpty()) {
                                        Text(
                                            text = note.title,
                                            color = Color.White,
                                            style = MaterialTheme.typography.labelLarge.copy(
                                                textDecoration = TextDecoration.Underline
                                            ),
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    } else {
                                        Text(
                                            text = note.content.replace("\n", " "),
                                            color = Color.White,
                                            style = MaterialTheme.typography.labelLarge,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(8.dp))
                                }
                            }
                        }
                        if (pair.size == 1) {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
        } else {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(240.dp)
                        .padding(horizontal = 12.dp)
                        .border(
                            BorderStroke(2.dp, Color.White),
                            shape = RoundedCornerShape(14.dp)
                        )
                        .background(
                            color = Color(0xFF303030),
                            shape = RoundedCornerShape(14.dp)
                        )
                        .clickable { onAddNoteClick() },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Toca para añadir una nota",
                        color = Color.White,
                        style = MaterialTheme.typography.labelLarge,
                    )
                }
            }
        }
    }
}



