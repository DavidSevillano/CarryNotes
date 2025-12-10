package com.burixer85.mynotesapp.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.burixer85.mynotesapp.presentation.model.QuickNote
import com.burixer85.mynotesapp.R
import com.burixer85.mynotesapp.presentation.model.Category

@Composable
fun CarryCreateCategoryDialog(
    categoryToEdit: Category? = null,
    onDismiss: () -> Unit,
    onConfirm: (Category) -> Unit,
    onDeleteConfirm: () -> Unit
) {

    var showDeleteConfirmationDialog by remember { mutableStateOf(false) }
    var isMainDialogVisible by remember { mutableStateOf(true) }

    var categoryName by remember(categoryToEdit) { mutableStateOf(categoryToEdit?.name ?: "") }
    var categoryNotes by remember(categoryToEdit) {
        mutableStateOf(
            categoryToEdit?.notes ?: emptyList()
        )
    }

    val isCategoryNameValid by remember(categoryName, categoryToEdit) {
        derivedStateOf {
            val nameIsNotBlank = categoryName.isNotBlank()
            val hasChanges = categoryToEdit?.name != categoryName

            nameIsNotBlank && (categoryToEdit == null || hasChanges)
        }
    }
    if (isMainDialogVisible) {
        Dialog(
            onDismissRequest = onDismiss,
            properties = DialogProperties(dismissOnClickOutside = false)
        ) {
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = Color(0xFF303030),
                border = BorderStroke(1.dp, Color.White)
            ) {
                Box(Modifier.fillMaxWidth()) {
                    Column(
                        modifier = Modifier
                            .padding(24.dp)
                            .fillMaxWidth()
                    ) {
                        val titleText = categoryToEdit?.name
                            ?: stringResource(R.string.CreateCategory_Dialog_Main_Text)

                        Text(
                            text = titleText,
                            modifier = Modifier
                                .padding(end = 40.dp),
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.White
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        CarryTextField(
                            value = categoryName,
                            onValueChange = { categoryName = it },
                            height = 80.dp,
                            label = stringResource(R.string.CreateCategory_Dialog_Text_Label),
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
                                        stringResource(R.string.CreateCategory_Dialog_TextButton_Cancel),
                                        style = MaterialTheme.typography.labelMedium
                                    )
                                }
                            }

                            val isEditing = categoryToEdit != null
                            val buttonText = if (isEditing) {
                                stringResource(R.string.CreateCategory_Dialog_Button_Update)
                            } else {
                                stringResource(R.string.CreateCategory_Dialog_Button_Save)
                            }

                            val buttonColor = if (isEditing) {
                                Color(0xFF2196F3)
                            } else {
                                Color(0xFF64B5F6)
                            }
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                val iconColor = if (isCategoryNameValid) buttonColor else Color.Gray

                                val saveIcon =
                                    if (isEditing) Icons.Default.Sync else Icons.Default.Save

                                Icon(
                                    imageVector = saveIcon,
                                    contentDescription = buttonText,
                                    tint = iconColor
                                )

                                TextButton(
                                    onClick = {
                                        val finalCategory = categoryToEdit?.copy(
                                            name = categoryName,
                                            notes = categoryNotes
                                        ) ?: Category(
                                            name = categoryName,
                                            notes = categoryNotes
                                        )
                                        onConfirm(finalCategory)
                                    },
                                    enabled = isCategoryNameValid,
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
                    if (categoryToEdit != null) {
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
                                contentDescription = "Borrar categoría",
                                tint = Color.White
                            )
                        }
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
                    stringResource(R.string.CreateCategory_Dialog_AlertDialog_Text_Title),
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White
                )
            },
            text = {
                Text(
                    stringResource(R.string.CreateCategory_Dialog_AlertDialog_Text_Content),
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
                            stringResource(R.string.CreateCategory_Dialog_AlertDialog_Button_Cancel),
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
                            stringResource(R.string.CreateCategory_Dialog_AlertDialog_Button_Delete),
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