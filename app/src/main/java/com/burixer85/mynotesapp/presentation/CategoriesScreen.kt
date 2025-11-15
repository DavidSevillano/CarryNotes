package com.burixer85.mynotesapp.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.burixer85.mynotesapp.R
import com.burixer85.mynotesapp.presentation.components.CarryAllCategories
import com.burixer85.mynotesapp.presentation.components.CarryFloatingActionButton

@Composable
fun CategoriesScreen(
    categoriesScreenViewModel: CategoriesScreenViewModel = viewModel(),
    onCategoryClick: (categoryId: Int, categoryName: String) -> Unit
) {

    val uiState by categoriesScreenViewModel.uiState.collectAsStateWithLifecycle()

    Box(modifier = Modifier.fillMaxSize().background(Color(0xFF212121))) {

            Column(Modifier.fillMaxSize()) {
                Text(
                    modifier = Modifier.padding(top = 24.dp, start = 24.dp, end = 24.dp),
                    text = stringResource(R.string.Main_Screen_Text_Tittle),
                    color = Color.White,
                    style = MaterialTheme.typography.headlineSmall
                )
                Box(
                    Modifier
                        .fillMaxWidth()
                        .padding(24.dp)

                        .height(60.dp)
                        .border(
                            BorderStroke(2.dp, Color.White),
                            shape = RoundedCornerShape(32.dp)
                        )
                        .background(
                            color = Color(0xFF303030),
                            shape = RoundedCornerShape(14.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {

                    Text(
                        text = stringResource(R.string.Main_Screen_Button_Achievements),
                        color = Color.White,
                        style = MaterialTheme.typography.labelLarge
                    )

                }
                Spacer(
                    modifier = Modifier.padding(12.dp),
                )
                if (uiState.categories.isNotEmpty()) {
                    CarryAllCategories(
                        categories = uiState.categories,
                        onCategoryClick = { category ->
                            onCategoryClick(category.id, category.name)
                        })
                } else {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = stringResource(R.string.Categories_Screen_Main_Text_No_Categories),
                            color = Color.White,
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier
                                .padding(bottom = 32.dp)
                        )

                        Box(
                            Modifier
                                .fillMaxWidth()
                                .height(240.dp)
                                .padding(12.dp)
                                .border(
                                    BorderStroke(2.dp, Color.White),
                                    shape = RoundedCornerShape(14.dp)
                                )
                                .background(
                                    color = Color(0xFF303030),
                                    shape = RoundedCornerShape(14.dp)
                                ),
                            contentAlignment = Alignment.Center
                        ) {

                            Text(
                                text = stringResource(R.string.Categories_Screen_Text_Box_Add_Category),
                                color = Color.White,
                                style = MaterialTheme.typography.labelLarge,
                            )
                        }

                    }
                }
            }
        }
    }

