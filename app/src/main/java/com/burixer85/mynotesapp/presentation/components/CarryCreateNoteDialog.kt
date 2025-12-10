package com.burixer85.mynotesapp.presentation.components

import androidx.activity.result.launch
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.burixer85.mynotesapp.R
import com.burixer85.mynotesapp.presentation.model.Category
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarryCreateNoteDialog(
    categories: List<Category>,
    initialTitle: String? = null,
    initialContent: String? = null,
    initialCategory: Category? = null,
    onDismiss: () -> Unit,
    onConfirm: (title: String, content: String, categoryId: Int) -> Unit,
    onCreateCategoryRequest: () -> Unit
) {

    val isEditing = initialTitle != null || initialContent != null

    var title by remember(initialTitle) { mutableStateOf(initialTitle ?: "") }
    var content by remember(initialContent) { mutableStateOf(initialContent ?: "") }
    var selectedCategory by remember(initialCategory) { mutableStateOf(initialCategory) }

    val contentScrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()

    val isFormValid by remember(title, content, selectedCategory) {
        derivedStateOf {
            val fieldsAreValid =
                title.isNotBlank() && content.isNotBlank() && selectedCategory != null
            if (!fieldsAreValid) {
                false
            } else {
                if (isEditing) {
                    title != initialTitle || content != initialContent || selectedCategory != initialCategory
                } else {
                    true
                }
            }
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
            if (categories.isEmpty() && !isEditing) {
                Column(
                    modifier = Modifier
                        .padding(24.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(R.string.CreateNote_Dialog_Text_No_Categories_Label),
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = stringResource(R.string.CreateNote_Dialog_Text_No_Categories_Content),
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFFE0E0E0),

                        )
                    Spacer(modifier = Modifier.height(20.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
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
                                    stringResource(R.string.CreateNote_Dialog_TextButton_Cancel),
                                    style = MaterialTheme.typography.labelMedium
                                )
                            }
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = stringResource(R.string.CreateNote_Dialog_Button_Save),
                                tint = Color(0xFF64B5F6)
                            )
                            TextButton(
                                onClick = {
                                    onCreateCategoryRequest()
                                },
                                colors = ButtonDefaults.textButtonColors(
                                    contentColor = Color(0xFF64B5F6),
                                    disabledContentColor = Color.Gray
                                ),
                                contentPadding = PaddingValues(6.dp)
                            ) {
                                Text(
                                    text = stringResource(R.string.CreateNote_Dialog_Button_No_Categories_Create),
                                    style = MaterialTheme.typography.labelMedium
                                )
                            }
                        }
                    }
                }
            } else {
                Column(
                    modifier = Modifier
                        .padding(24.dp)
                        .fillMaxWidth()
                        .heightIn(max = 520.dp)
                ) {
                    Text(
                        text = if (isEditing) stringResource(R.string.CreateNote_Dialog_Main_Text_Edit) else stringResource(
                            R.string.CreateNote_Dialog_Main_Text_Create
                        ),
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    CarryTextFieldWithDropdown(
                        items = categories.map { it.name },
                        selectedItem = selectedCategory?.name
                            ?: stringResource(R.string.CreateNote_Dialog_Text_DropdownNo_Category),
                        onItemSelected = { selectedName ->
                            selectedCategory = categories.find { it.name == selectedName }
                        },
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    CarryTextField(
                        value = title,
                        onValueChange = { title = it },
                        label = stringResource(R.string.CreateNote_Dialog_Text_Label_Title),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Box(
                        modifier = Modifier
                            .weight(1f, fill = false)
                            .verticalScroll(contentScrollState)
                    ) {
                        CarryTextField(
                            value = content,
                            onValueChange = { content = it
                                coroutineScope.launch {
                                    contentScrollState.animateScrollTo(contentScrollState.maxValue)
                                }},
                            label = stringResource(R.string.CreateNote_Dialog_Text_Label_Content),
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
                                    stringResource(R.string.CreateNote_Dialog_TextButton_Cancel),
                                    style = MaterialTheme.typography.labelMedium
                                )
                            }
                        }

                        val buttonText = if (isEditing) {
                            stringResource(R.string.CreateNote_Dialog_Button_Update)
                        } else {
                            stringResource(R.string.CreateNote_Dialog_Button_Save)
                        }

                        val buttonColor = if (isEditing) Color(0xFF2196F3) else Color(0xFF64B5F6)
                        val saveIcon = if (isEditing) Icons.Default.Sync else Icons.Default.Save

                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            val iconColor = if (isFormValid) buttonColor else Color.Gray

                            Icon(
                                imageVector = saveIcon,
                                contentDescription = buttonText,
                                tint = iconColor
                            )
                            TextButton(
                                onClick = {
                                    onConfirm(title, content, selectedCategory!!.id)
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
}