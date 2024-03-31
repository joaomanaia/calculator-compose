package presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.hoc081098.kmp.viewmodel.koin.compose.koinKmpViewModel
import presentation.components.button.primary.ButtonGrid
import presentation.components.button.secondary.SecondaryButtonGrid
import presentation.components.expression.ExpressionContent
import core.presentation.theme.spacing

@Composable
internal fun HomeScreen(
    windowSizeClass: WindowSizeClass,
    homeViewModel: HomeViewModel = koinKmpViewModel()
) {
    val uiState by homeViewModel.uiState.collectAsState()
    val result by homeViewModel.result.collectAsState()

    HomeScreenImpl(
        uiState = uiState,
        result = result,
        windowSizeClass = windowSizeClass,
        onEvent = homeViewModel::onEvent
    )
}

@Composable
fun HomeScreenImpl(
    uiState: HomeUiState,
    result: String,
    windowSizeClass: WindowSizeClass,
    onEvent: (event: HomeUiEvent) -> Unit
) {
    val showVerticalContent = windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact

    Surface {
        if (showVerticalContent) {
            HomePortraitContent(
                uiState = uiState,
                result = result,
                onEvent = onEvent
            )
        } else {
            HomeLandscapeContent(
                uiState = uiState,
                result = result,
                windowSizeClass = windowSizeClass,
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
    val spaceSmall = MaterialTheme.spacing.small
    val spaceMedium = MaterialTheme.spacing.medium

    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (expressionContent, buttonsGrid) = createRefs()

        ExpressionContent(
            modifier = Modifier.constrainAs(expressionContent) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(parent.top)
                bottom.linkTo(buttonsGrid.top, spaceSmall)

                height = Dimension.fillToConstraints
            },
            isPortrait = true,
            currentExpression = uiState.currentExpression,
            result = result,
            angleType = uiState.angleType,
            shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp),
            updateTextFieldValue = { value ->
                onEvent(HomeUiEvent.UpdateTextFieldValue(value))
            }
        )

        Column(
            modifier = Modifier.constrainAs(buttonsGrid) {
                start.linkTo(parent.start, spaceMedium)
                end.linkTo(parent.end, spaceMedium)
                bottom.linkTo(parent.bottom, spaceMedium)

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
    windowSizeClass: WindowSizeClass,
    onEvent: (event: HomeUiEvent) -> Unit
) {
    val spaceMedium = MaterialTheme.spacing.medium

    val historyComponentWidth = if (windowSizeClass.widthSizeClass == WindowWidthSizeClass.Expanded) {
        300.dp
    } else {
        200.dp
    }

    ConstraintLayout(
        modifier = Modifier.fillMaxSize().padding(spaceMedium)
    ) {
        val (expressionContent, buttonsGrid, historyComponent) = createRefs()

        HistoryComponent(
            modifier = Modifier.constrainAs(historyComponent) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)

                height = Dimension.fillToConstraints
                width = Dimension.value(historyComponentWidth)
            }
        )

        ExpressionContent(
            modifier = Modifier.constrainAs(expressionContent) {
                start.linkTo(historyComponent.end, spaceMedium)
                end.linkTo(parent.end)
                top.linkTo(parent.top)
                bottom.linkTo(buttonsGrid.top)

                width = Dimension.fillToConstraints
                height = Dimension.fillToConstraints
            },
            isPortrait = false,
            currentExpression = uiState.currentExpression,
            result = result,
            angleType = uiState.angleType,
            updateTextFieldValue = { value ->
                onEvent(HomeUiEvent.UpdateTextFieldValue(value))
            }
        )

        Row(
            modifier = Modifier.constrainAs(buttonsGrid) {
                start.linkTo(historyComponent.end, spaceMedium)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
                top.linkTo(expressionContent.bottom, spaceMedium)

                width = Dimension.fillToConstraints
                height = Dimension.fillToConstraints
            },
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium)
        ) {
            SecondaryButtonGrid(
                modifier = Modifier.fillMaxHeight(),
                isPortrait = false,
                buttonGridExpanded = uiState.moreActionsExpanded,
                angleType = uiState.angleType,
                isInverse = uiState.isInverse,
                onActionClick = { action ->
                    onEvent(HomeUiEvent.OnButtonActionClick(action))
                },
                onMoreActionsClick = { onEvent(HomeUiEvent.OnChangeMoreActionsClick) }
            )
            ButtonGrid(
                modifier = Modifier.fillMaxHeight(),
                isPortrait = false,
                onActionClick = { action ->
                    onEvent(HomeUiEvent.OnButtonActionClick(action))
                },
            )
        }
    }
}

@Composable
private fun HistoryComponent(
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier,
        tonalElevation = 4.dp,
        shape = RoundedCornerShape(20.dp)
    ) {
        LazyColumn {
            item {
                Surface(
                    tonalElevation = 8.dp
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                vertical = MaterialTheme.spacing.small,
                                horizontal = MaterialTheme.spacing.medium
                            ),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "History",
                            style = MaterialTheme.typography.bodyLarge
                        )
                        IconButton(
                            onClick = { /*TODO*/ },
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.MoreVert,
                                contentDescription = "History more options",
                            )
                        }
                    }
                }
            }
        }
    }
}
