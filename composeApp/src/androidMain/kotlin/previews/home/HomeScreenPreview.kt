package previews.home

import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.*
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.PreviewLightDark
import core.presentation.theme.CalculatorTheme
import presentation.home.HomeScreenImpl
import presentation.home.HomeUiEvent
import presentation.home.HomeUiState

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
@PreviewLightDark
private fun HomeScreenPreview() {
    var buttonGridExpanded by remember { mutableStateOf(false) }

    CalculatorTheme {
        Surface {
            HomeScreenImpl(
                uiState = HomeUiState(
                    currentExpression = TextFieldValue("22+1"),
                    moreActionsExpanded = buttonGridExpanded
                ),
                result = "23",
                windowSizeClass = calculateWindowSizeClass(),
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
