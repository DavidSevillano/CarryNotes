package com.burixer85.mynotesapp.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.burixer85.mynotesapp.presentation.model.QuickNote
import com.burixer85.mynotesapp.R

@Composable
fun CarryCreateQuickNoteDialog(
    noteToEdit: QuickNote? = null,
    onDismiss: () -> Unit,
    onConfirm: (QuickNote) -> Unit
) {
    var title by remember(noteToEdit) { mutableStateOf(noteToEdit?.title ?: "") }
    var content by remember(noteToEdit) { mutableStateOf(noteToEdit?.content ?: "") }

    val isFormValid by remember(title, content, noteToEdit) {
        derivedStateOf {
            val titleIsNotBlank = title.isNotBlank()
            val contentIsNotBlank = content.isNotBlank()

            if (noteToEdit == null) {
                titleIsNotBlank && contentIsNotBlank
            } else {
                val hasChanges = title != noteToEdit.title || content != noteToEdit.content
                titleIsNotBlank && contentIsNotBlank && hasChanges
            }
        }
    }
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnClickOutside = false
        )
    ) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color(0xFF303030),
            border = BorderStroke(1.dp, Color.White)
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.CreateQuickNote_Dialog_Main_Text),
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(20.dp))

                CarryTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = stringResource(R.string.CreateQuickNote_Dialog_Text_Label_Title),
                    singleLine = true,
                )

                Spacer(modifier = Modifier.height(16.dp))

                CarryTextField(
                    value = content,
                    onValueChange = { content = it },
                    height = 120.dp,
                    label = stringResource(R.string.CreateQuickNote_Dialog_Text_Label_Content),
                )

                Spacer(modifier = Modifier.height(28.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = Color(0xFFFF7043)
                        )

                        TextButton(
                            onClick = onDismiss, colors = ButtonDefaults.textButtonColors(
                                contentColor = Color(0xFFFF7043)
                            ),
                            contentPadding = PaddingValues(6.dp)

                        ) {
                            Text(
                                stringResource(R.string.CreateQuickNote_Dialog_TextButton_Cancel),
                                style = MaterialTheme.typography.labelMedium
                            )
                        }
                    }
                    val isEditing = noteToEdit != null
                    val buttonText = if (isEditing) {
                        stringResource(R.string.CreateQuickNote_Dialog_Button_Update)
                    } else {
                        stringResource(R.string.CreateQuickNote_Dialog_Button_Save)
                    }

                    val buttonColor = if (isEditing) {
                        Color(0xFF2196F3)
                    } else {
                        Color(0xFF64B5F6)
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        val iconColor = if (isFormValid) {
                            Color(0xFF64B5F6)
                        } else {
                            Color.Gray
                        }
                        Icon(
                            imageVector = Icons.Default.Save,
                            contentDescription = buttonText,
                            tint = iconColor
                        )

                        TextButton(
                            onClick = {
                                val finalNote = noteToEdit?.copy(
                                    title = title,
                                    content = content
                                ) ?: QuickNote(
                                    title = title,
                                    content = content
                                )
                                onConfirm(finalNote)
                            },
                            enabled = isFormValid,
                            colors = ButtonDefaults.textButtonColors(
                                contentColor = buttonColor,
                                disabledContentColor = Color.Gray
                            ),
                            contentPadding = PaddingValues(6.dp)

                        ) {
                            Text(
                                text = buttonText,
                                style = MaterialTheme.typography.labelMedium
                            )
                        }
                    }
                }
            }
        }
    }
}