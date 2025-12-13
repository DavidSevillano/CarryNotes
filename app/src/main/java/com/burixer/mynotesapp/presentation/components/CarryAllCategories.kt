package com.burixer85.mynotesapp.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.burixer85.mynotesapp.presentation.model.Category
import com.burixer85.mynotesapp.R


@Composable
fun CarryAllCategories(
    categories: List<Category>,
    onCategoryClick: (Category) -> Unit,
    onAddCategoryClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp, start = 16.dp, end = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.width(48.dp))

            Text(
                text = stringResource(R.string.Categories_Screen_Main_Text),
                color = Color.White,
                style = MaterialTheme.typography.titleLarge
            )

            IconButton(
                onClick = { onAddCategoryClick() },
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
                    contentDescription = "Añadir Categoría",
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            items(categories) { category ->
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        Modifier
                            .height(80.dp)
                            .weight(1f)
                            .border(
                                BorderStroke(2.dp, Color.White),
                                shape = RoundedCornerShape(14.dp)
                            )
                            .background(
                                color = Color(0xFF303030),
                                shape = RoundedCornerShape(14.dp)
                            )
                            .clickable { onCategoryClick(category) }
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.Center)
                                .padding(horizontal = 16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = category.name.replaceFirstChar { if (it.isLowerCase()) it.uppercase() else it.toString() },                                color = Color.White,
                                style = MaterialTheme.typography.labelLarge,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.weight(1f, fill = false)
                            )
                            Text(
                                text = "${category.notes.size} notas",
                                color = Color.White,
                                style = MaterialTheme.typography.labelMedium,
                            )
                        }
                    }
                }
            }
        }
    }
}
