package com.burixer85.mynotesapp.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import com.burixer85.mynotesapp.presentation.model.QuickNote
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import com.burixer85.mynotesapp.R

@Composable
fun CarryAllQuickNotes(
    quickNotes: List<QuickNote>,
    onQuickNoteClick: (QuickNote) -> Unit,
    onAddQuickNoteClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (quickNotes.isNotEmpty()) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp, start = 16.dp, end = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Spacer(modifier = Modifier.width(48.dp))

                Text(
                    text = stringResource(R.string.QuickNotes_Screen_Main_Text),
                    color = Color.White,
                    style = MaterialTheme.typography.titleLarge
                )

                IconButton(
                    onClick = { onAddQuickNoteClick() },
                    modifier = Modifier.size(48.dp) .background(
                        color = Color(0xFF303030),
                        shape = CircleShape
                    ).border(
                        width = 1.dp,
                        color = Color.White,
                        shape = CircleShape
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Añadir Nota Rápida",
                        tint = Color.White,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(quickNotes.chunked(2)) { pair ->
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        pair.forEach { quickNote ->
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
                                    .clickable { onQuickNoteClick(quickNote) }
                            ) {
                                Column(
                                    modifier = Modifier
                                        .align(Alignment.Center)
                                        .padding(8.dp)
                                ) {
                                    if (quickNote.title.isNotEmpty()) {
                                        Text(
                                            text = quickNote.title,
                                            color = Color.White,
                                            style = MaterialTheme.typography.labelLarge.copy(
                                                textDecoration = TextDecoration.Underline
                                            ),
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    } else {
                                        Text(
                                            text = quickNote.content.replace("\n", " "),
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
                Text(
                    text = stringResource(R.string.QuickNotes_Screen_Main_Text_No_Quicknotes),
                    color = Color.White,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(bottom = 32.dp)
                )

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
                        .clickable { onAddQuickNoteClick() },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(R.string.QuickNotes_Screen_Text_Box_Add_QuickNote),
                        color = Color.White,
                        style = MaterialTheme.typography.labelLarge,
                    )
                }
            }
        }
    }

}
