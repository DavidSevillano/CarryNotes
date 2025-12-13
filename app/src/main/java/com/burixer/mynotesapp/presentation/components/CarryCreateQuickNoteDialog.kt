package com.burixer85.mynotesapp.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Sync
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
import com.burixer85.mynotesapp.R

@Composable
fun CarryCreateQuickNoteDialog(
    initialTitle: String? = null,
    initialContent: String? = null,
    onDismiss: () -> Unit,
    onConfirm: (title: String, content: String) -> Unit,
) {

    val isEditing = initialContent != null

    var isTitleEnabled by remember { mutableStateOf(initialTitle?.isNotBlank() ?: false) }
    var title by remember(initialTitle) { mutableStateOf(initialTitle ?: "") }
    var content by remember(initialContent) { mutableStateOf(initialContent ?: "") }

    val isFormValid by remember(title, content, isEditing, isTitleEnabled) {
        derivedStateOf {
            val contentIsNotBlank = content.isNotBlank()
            val titleIsValid = if (isTitleEnabled) title.isNotBlank() else true

            val hasChanges = if (isEditing) {
                (if (isTitleEnabled) title else "") != (initialTitle ?: "") || content != initialContent
            } else {
                content.isNotBlank() || title.isNotBlank()
            }

            contentIsNotBlank && titleIsValid && hasChanges
        }
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(dismissOnClickOutside = false)
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
                    .heightIn(max = 500.dp)
            ) {
                Text(
                    text = if (isEditing) stringResource(R.string.CreateQuickNote_Dialog_Main_Text_Edit) else stringResource(
                        R.string.CreateQuickNote_Dialog_Main_Text_Create
                    ),
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.CreateQuickNote_Dialog_Text_Label_Add_Title),
                        color = Color.White,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Switch(
                        checked = isTitleEnabled,
                        onCheckedChange = { isEnabled ->
                            isTitleEnabled = isEnabled
                            if (!isEnabled) {
                                title = ""
                            }
                        },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = MaterialTheme.colorScheme.primary,
                            checkedTrackColor = MaterialTheme.colorScheme.primaryContainer,
                            uncheckedThumbColor = MaterialTheme.colorScheme.outline,
                            uncheckedTrackColor = MaterialTheme.colorScheme.surfaceVariant,
                        )
                    )
                }

                AnimatedVisibility(
                    visible = isTitleEnabled,
                    enter = slideInVertically(initialOffsetY = { -it }),
                    exit = fadeOut(animationSpec = tween(durationMillis = 200))
                ) {
                    Column {
                        Spacer(modifier = Modifier.height(8.dp))
                        CarryTextField(
                            value = title,
                            onValueChange = { title = it },
                            label = stringResource(R.string.CreateQuickNote_Dialog_Text_Label_Title),
                            singleLine = true,
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Box(
                    modifier = Modifier
                        .weight(1f, fill = false)
                        .verticalScroll(rememberScrollState())
                ) {
                    CarryTextField(
                        value = content,
                        onValueChange = { content = it },
                        label = stringResource(R.string.CreateQuickNote_Dialog_Text_Label_Content),
                    )
                }

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
                        val iconColor = if (isFormValid) buttonColor else Color.Gray

                        val saveIcon = if (isEditing) Icons.Default.Sync else Icons.Default.Save

                        Icon(
                            imageVector = saveIcon,
                            contentDescription = buttonText,
                            tint = iconColor
                        )

                        TextButton(
                            onClick = {
                                val finalTitle = if (isTitleEnabled) title else ""
                                onConfirm(finalTitle, content)
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