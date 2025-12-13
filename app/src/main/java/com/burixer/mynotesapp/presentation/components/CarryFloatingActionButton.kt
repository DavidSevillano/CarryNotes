package com.burixer85.mynotesapp.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.automirrored.outlined.Article
import androidx.compose.material.icons.automirrored.outlined.StickyNote2
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.outlined.Article
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material.icons.outlined.EditNote
import androidx.compose.material.icons.outlined.StickyNote2
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.burixer85.mynotesapp.R

@Composable
fun CarryFloatingActionButton(
    modifier: Modifier = Modifier,
    onOptionSelected: (String) -> Unit
) {
    var showMenu by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .wrapContentSize(Alignment.TopStart)

    ) {
        FloatingActionButton(
            onClick = {
                showMenu = true
            }
        ) {
            Icon(Icons.Default.Add, contentDescription = "Añadir nota o categoría")
        }

        DropdownMenu(
            modifier = Modifier
                .border(
                    BorderStroke(2.dp, Color.White),
                    shape = RoundedCornerShape(14.dp)
                )
                .background(
                    color = Color(0xFF303030),
                    shape = RoundedCornerShape(14.dp)
                ),
            expanded = showMenu,
            onDismissRequest = { showMenu = false }
        ) {
            DropdownMenuItem(
                text = { Text(stringResource(R.string.Main_Screen_FAB_Add_QuickNote)) },
                onClick = {
                    showMenu = false
                    onOptionSelected("quicknote")
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.quicknote_icon),
                        contentDescription = "Añadir nota rápida"
                    )
                }
            )
            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 16.dp),
                thickness = DividerDefaults.Thickness,
                color = Color.Gray
            )

            DropdownMenuItem(
                text = { Text(stringResource(R.string.Main_Screen_FAB_Add_Category)) },
                onClick = {
                    showMenu = false
                    onOptionSelected("category")
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Category,
                        contentDescription = "Añadir categoría"
                    )
                }
            )

            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 16.dp),
                thickness = DividerDefaults.Thickness,
                color = Color.Gray
            )

            DropdownMenuItem(
                text = { Text(stringResource(R.string.Main_Screen_FAB_Add_Note)) },
                onClick = {
                    showMenu = false
                    onOptionSelected("note")
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Outlined.Article,
                        contentDescription = "Añadir nota"
                    )
                }
            )
        }
    }
}