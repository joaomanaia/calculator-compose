package com.infinitepower.calculator.compose.ui.home

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import com.infinitepower.calculator.compose.ui.components.button.primary.ButtonGrid
import com.infinitepower.calculator.compose.ui.components.button.secondary.SecondaryButtonGrid
import com.infinitepower.calculator.compose.ui.components.expression_content.ExpressionContent
import com.infinitepower.calculator.compose.ui.theme.CalculatorTheme
import com.infinitepower.calculator.compose.ui.theme.spacing

@Composable
internal fun HomeScreen(
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by homeViewModel.uiState.collectAsState()

    HomeScreenImpl(
        uiState = uiState,
        onEvent = homeViewModel::onEvent
    )
}

@Composable
fun HomeScreenImpl(
    uiState: HomeUiState,
    onEvent: (event: HomeUiEvent) -> Unit
) {
    val localConfiguration = LocalConfiguration.current

    val isPortrait = remember(localConfiguration.orientation) {
        localConfiguration.orientation == Configuration.ORIENTATION_PORTRAIT
    }

    Surface {
        if (isPortrait) {
            HomePortraitContent(
                uiState = uiState,
                onEvent = onEvent
            )
        } else {
            HomeLandscapeContent(
                uiState = uiState,
                onEvent = onEvent
            )
        }
    }
}

@Composable
private fun HomePortraitContent(
    uiState: HomeUiState,
    onEvent: (event: HomeUiEvent) -> Unit
) {
    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (expressionContent, buttonsGrid) = createRefs()

        ExpressionContent(
            modifier = Modifier.constrainAs(expressionContent) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(parent.top)
                bottom.linkTo(buttonsGrid.top)

                height = Dimension.fillToConstraints
            },
            isPortrait = true,
            currentExpression = uiState.currentExpression,
            result = uiState.result,
            updateTextFieldValue = { value ->
                onEvent(HomeUiEvent.UpdateTextFieldValue(value))
            }
        )

        Column(
            modifier = Modifier.constrainAs(buttonsGrid) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)

                height = Dimension.fillToConstraints
            }
        ) {
            SecondaryButtonGrid(
                modifier = Modifier.fillMaxWidth(),
                isPortrait = true,
                onActionClick = { action ->
                    onEvent(HomeUiEvent.OnButtonActionClick(action))
                },
                onMoreActionsClick = { onEvent(HomeUiEvent.OnChangeMoreActionsClick) },
                buttonGridExpanded = uiState.moreActionsExpanded
            )
            ButtonGrid(
                modifier = Modifier.fillMaxWidth(),
                isPortrait = true,
                onActionClick = { action ->
                    onEvent(HomeUiEvent.OnButtonActionClick(action))
                },
            )
        }
    }
}

@Composable
private fun HomeLandscapeContent(
    uiState: HomeUiState,
    onEvent: (event: HomeUiEvent) -> Unit
) {
    Column {
        ExpressionContent(
            modifier = Modifier.fillMaxWidth(),
            isPortrait = false,
            currentExpression = uiState.currentExpression,
            result = uiState.result,
            updateTextFieldValue = { value ->
                onEvent(HomeUiEvent.UpdateTextFieldValue(value))
            }
        )
        Row(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = MaterialTheme.spacing.small),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            SecondaryButtonGrid(
                modifier = Modifier.weight(1f),
                isPortrait = false,
                onActionClick = { action ->
                    onEvent(HomeUiEvent.OnButtonActionClick(action))
                },
                onMoreActionsClick = { onEvent(HomeUiEvent.OnChangeMoreActionsClick) },
                buttonGridExpanded = true
            )
            ButtonGrid(
                modifier = Modifier.weight(2f),
                isPortrait = false,
                onActionClick = { action ->
                    onEvent(HomeUiEvent.OnButtonActionClick(action))
                },
            )
        }
    }
}

@Composable
@PreviewLightDark
private fun HomeScreenPreview() {
    var buttonGridExpanded by remember { mutableStateOf(false) }

    CalculatorTheme {
        Surface {
            HomeScreenImpl(
                uiState = HomeUiState(
                    currentExpression = TextFieldValue("22+1"),
                    result = "23",
                    moreActionsExpanded = buttonGridExpanded
                ),
                onEvent = { event ->
                    when (event) {
                        is HomeUiEvent.OnChangeMoreActionsClick -> {
                            buttonGridExpanded = !buttonGridExpanded
                        }

                        else -> {}
                    }
                }
            )
        }
    }
}
