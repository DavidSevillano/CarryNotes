package com.burixer85.mynotesapp.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
    onDismiss: () -> Unit,
    onConfirm: (QuickNote) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    val isFormValid by remember { derivedStateOf { title.isNotBlank() && content.isNotBlank() } }

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
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(onClick = onDismiss) {
                        Text(
                            stringResource(R.string.CreateQuickNote_Dialog_TextButton_Cancel),
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.labelLarge
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(
                        onClick = {
                            val newNote = QuickNote(title = title, content = content)
                            onConfirm(newNote)
                        },
                        enabled = isFormValid
                    ) {
                        Text(
                            text = stringResource(R.string.CreateQuickNote_Dialog_Button_Save),
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                }
            }
        }
    }
}