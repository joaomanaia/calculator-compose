package presentation.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.hoc081098.kmp.viewmodel.koin.compose.koinKmpViewModel
import presentation.components.button.primary.ButtonGrid
import presentation.components.button.secondary.SecondaryButtonGrid
import presentation.components.expression.ExpressionContent
import core.presentation.theme.spacing

@Composable
internal fun HomeScreen(
    homeViewModel: HomeViewModel = koinKmpViewModel()
) {
    val uiState by homeViewModel.uiState.collectAsState()
    val result by homeViewModel.result.collectAsState()

    HomeScreenImpl(
        uiState = uiState,
        result = result,
        onEvent = homeViewModel::onEvent
    )
}

@Composable
fun HomeScreenImpl(
    uiState: HomeUiState,
    result: String,
    onEvent: (event: HomeUiEvent) -> Unit
) {
    Surface {
        if (true) { // TODO: LocalViewConfiguration.current.orientation == Configuration.ORIENTATION_PORTRAIT
            HomePortraitContent(
                uiState = uiState,
                result = result,
                onEvent = onEvent
            )
        } else {
            HomeLandscapeContent(
                uiState = uiState,
                result = result,
                onEvent = onEvent
            )
        }
    }
}

@Composable
private fun HomePortraitContent(
    uiState: HomeUiState,
    result: String,
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
            result = result,
            angleType = uiState.angleType,
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
                buttonGridExpanded = uiState.moreActionsExpanded,
                angleType = uiState.angleType,
                isInverse = uiState.isInverse,
                onActionClick = { action ->
                    onEvent(HomeUiEvent.OnButtonActionClick(action))
                },
                onMoreActionsClick = { onEvent(HomeUiEvent.OnChangeMoreActionsClick) }
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
    result: String,
    onEvent: (event: HomeUiEvent) -> Unit
) {
    Column {
        ExpressionContent(
            modifier = Modifier.fillMaxWidth(),
            isPortrait = false,
            currentExpression = uiState.currentExpression,
            result = result,
            angleType = uiState.angleType,
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
                angleType = uiState.angleType,
                isInverse = uiState.isInverse,
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
