package com.burixer85.mynotesapp.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
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
import com.burixer85.mynotesapp.presentation.model.Category

@Composable
fun CarryCreateCategoryDialog(
    categoryToEdit: Category? = null,
    onDismiss: () -> Unit,
    onConfirm: (Category) -> Unit
) {

    var categoryName by remember(categoryToEdit) { mutableStateOf(categoryToEdit?.name ?: "") }
    var categoryNotes by remember(categoryToEdit) { mutableStateOf(categoryToEdit?.notes ?: emptyList()) }

    val isCategoryNameValid by remember(categoryName) {
        derivedStateOf {
            categoryName.isNotBlank()
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
            ) {
                Text(
                    text = stringResource(R.string.CreateCategory_Dialog_Main_Text),
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(20.dp))

                CarryTextField(
                    value = categoryName,
                    onValueChange = { categoryName = it },
                    height = 80.dp,
                    label = stringResource(R.string.CreateQuickNote_Dialog_Text_Label),
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
                        Icon(
                            imageVector = Icons.Default.Save,
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
        }
    }
}