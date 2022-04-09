package com.infinitepower.calculator.compose.ui.home

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.infinitepower.calculator.compose.ui.components.button.ButtonGrid
import com.infinitepower.calculator.compose.ui.components.expression_content.ExpressionContent
import com.infinitepower.calculator.compose.ui.theme.CalculatorTheme

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
    Surface {
        Column {
            ExpressionContent(
                modifier = Modifier.weight(1f),
                currentExpression = uiState.currentExpression,
                result = uiState.result,
                updateTextFieldValue = { value ->
                    onEvent(HomeUiEvent.UpdateTextFieldValue(value))
                }
            )
            ButtonGrid(
                modifier = Modifier.fillMaxWidth(),
                onActionClick = { action ->
                    onEvent(HomeUiEvent.OnButtonActionClick(action))
                }
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
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