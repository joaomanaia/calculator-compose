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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
    val isPortrait = LocalConfiguration.current.orientation == Configuration.ORIENTATION_PORTRAIT

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
    Column {
        ExpressionContent(
            modifier = Modifier.weight(1f),
            isPortrait = true,
            currentExpression = uiState.currentExpression,
            result = uiState.result,
            updateTextFieldValue = { value ->
                onEvent(HomeUiEvent.UpdateTextFieldValue(value))
            }
        )
        SecondaryButtonGrid(
            modifier = Modifier.fillMaxWidth(),
            isPortrait = true,
            onActionClick = { action ->
                onEvent(HomeUiEvent.OnButtonActionClick(action))
            },
            onMoreActionsClick = { expanded ->
                onEvent(HomeUiEvent.OnChangeMoreActionsState(expanded))
            },
            buttonGridExpanded = uiState.moreActionsExpanded
        )
        ButtonGrid(
            modifier = Modifier.fillMaxWidth(),
            isPortrait = true,
            onActionClick = { action ->
                onEvent(HomeUiEvent.OnButtonActionClick(action))
            },
            buttonGridExpanded = uiState.moreActionsExpanded
        )
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
                onMoreActionsClick = { expanded ->
                    onEvent(HomeUiEvent.OnChangeMoreActionsState(expanded))
                },
                buttonGridExpanded = true
            )
            ButtonGrid(
                modifier = Modifier.weight(2f),
                isPortrait = false,
                onActionClick = { action ->
                    onEvent(HomeUiEvent.OnButtonActionClick(action))
                },
                buttonGridExpanded = !uiState.moreActionsExpanded
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
@Preview(
    showBackground = true,
    device = "spec:shape=Normal,width=2340,height=1080,unit=px,dpi=440",
)
private fun HomeScreenPreview() {
    CalculatorTheme {
        Surface {
            HomeScreenImpl(
                uiState = HomeUiState(
                    currentExpression = TextFieldValue("2 + 1"),
                    result = "3"
                ),
                onEvent = {}
            )
        }
    }
}