package com.burixer85.mynotesapp.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Update
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.burixer85.mynotesapp.R
import com.burixer85.mynotesapp.presentation.model.QuickNote

@Composable
fun CarryQuickNoteDialog(
    note: QuickNote,
    onDismiss: () -> Unit,
    onEdit: () -> Unit,
    onDeleteConfirm: () -> Unit
) {
    var showDeleteConfirmationDialog by remember { mutableStateOf(false) }

    var isMainDialogVisible by remember { mutableStateOf(true) }

    if (isMainDialogVisible) {
        Dialog(onDismissRequest = onDismiss) {
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = Color(0xFF303030),
                border = BorderStroke(1.dp, Color.White)
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp)
                    ) {
                        if (note.title.isNotEmpty()) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = note.title,
                                    modifier = Modifier
                                        .padding(end = 40.dp),
                                    style = MaterialTheme.typography.titleSmall,
                                    color = Color.White,

                                    )
                            }

                            Spacer(modifier = Modifier.height(32.dp))

                            Text(
                                text = note.content,
                                style = MaterialTheme.typography.bodyLarge,
                                color = Color.White
                            )

                        }else{
                            Text(
                                text = note.content,
                                modifier = Modifier
                                    .padding(end = 40.dp),
                                style = MaterialTheme.typography.bodyLarge,
                                color = Color.White)
                        }
                        Spacer(modifier = Modifier.height(16.dp))


                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                modifier = Modifier.clickable { onEdit() },
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    Icons.Default.Edit,
                                    contentDescription = "Editar",
                                    tint = Color(0xFF64B5F6)
                                )
                                TextButton(
                                    onClick = onEdit, colors = ButtonDefaults.textButtonColors(
                                        contentColor = Color(0xFF64B5F6)
                                    ),
                                    contentPadding = PaddingValues(6.dp)

                                ) {
                                    Text(
                                        text = stringResource(R.string.QuickNote_Dialog_TextButton_Edit),
                                        style = MaterialTheme.typography.labelMedium,
                                    )
                                }

                            }
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    Icons.Default.Close,
                                    contentDescription = "Cerrar",
                                    tint = Color(0xFFFF7043)
                                )
                                TextButton(
                                    onClick = onDismiss, colors = ButtonDefaults.textButtonColors(
                                        contentColor = Color(0xFFFF7043)
                                    ),
                                    contentPadding = PaddingValues(6.dp)

                                ) {
                                    Text(
                                        text = stringResource(R.string.QuickNote_Dialog_TextButton_Close),
                                        style = MaterialTheme.typography.labelMedium,
                                    )
                                }
                            }

                        }
                    }

                    Box(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(top = 16.dp, end = 16.dp)
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(Color.Red)
                            .clickable {
                                showDeleteConfirmationDialog = true
                                isMainDialogVisible = false
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Delete,
                            contentDescription = "Borrar nota",
                            tint = Color.White
                        )
                    }
                }
            }
        }
    }
    if (showDeleteConfirmationDialog) {
        AlertDialog(
            onDismissRequest = {},
            title = {
                Text(
                    stringResource(R.string.QuickNote_Dialog_AlertDialog_Text_Title),
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White
                )
            },
            text = {
                Text(
                    stringResource(R.string.QuickNote_Dialog_AlertDialog_Text_Content),
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.LightGray
                )
            },

            confirmButton = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(
                        onClick = {
                            showDeleteConfirmationDialog = false
                            isMainDialogVisible = true
                        }
                    ) {
                        Text(
                            stringResource(R.string.QuickNote_Dialog_AlertDialog_Button_Cancel),
                            color = MaterialTheme.colorScheme.primary
                        )
                    }


                    TextButton(
                        onClick = {
                            showDeleteConfirmationDialog = false
                            onDeleteConfirm()
                            onDismiss()
                        }
                    ) {
                        Text(
                            stringResource(R.string.QuickNote_Dialog_AlertDialog_Button_Delete),
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            },
            containerColor = Color(0xFF2C2C2C),
            titleContentColor = Color.White,
            textContentColor = Color.LightGray
        )
    }
}