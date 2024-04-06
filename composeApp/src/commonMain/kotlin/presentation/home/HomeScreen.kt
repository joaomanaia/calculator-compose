package presentation.home

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
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.*
import com.hoc081098.kmp.viewmodel.koin.compose.koinKmpViewModel
import presentation.components.button.secondary.SecondaryButtonGrid
import presentation.components.expression.ExpressionContent
import core.presentation.theme.spacing
import presentation.components.button.primary.ButtonGrid

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
    Surface {
        HomeContent(
            uiState = uiState,
            result = result,
            windowSizeClass = windowSizeClass,
            onEvent = onEvent
        )
    }
}

@Composable
private fun HomeContent(
    uiState: HomeUiState,
    result: String,
    windowSizeClass: WindowSizeClass,
    onEvent: (event: HomeUiEvent) -> Unit
) {
    val spaceMedium = MaterialTheme.spacing.medium

    val showLeftExpressionHistory = windowSizeClass.widthSizeClass == WindowWidthSizeClass.Expanded
    val verticalContent = windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact

    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (expressionContent, primaryButtons, secondaryButtons, historyComponent) = createRefs()

        if (showLeftExpressionHistory) {
            HistoryComponent(
                modifier = Modifier.constrainAs(historyComponent) {
                    start.linkTo(parent.start, spaceMedium)
                    top.linkTo(parent.top, spaceMedium)
                    bottom.linkTo(parent.bottom, spaceMedium)

                    height = Dimension.fillToConstraints
                    width = Dimension.value(300.dp)
                }
            )
        }

        ExpressionContent(
            modifier = Modifier.constrainAs(expressionContent) {
                if (showLeftExpressionHistory) {
                    start.linkTo(historyComponent.end, spaceMedium)
                } else {
                    start.linkTo(parent.start, if (verticalContent) 0.dp else spaceMedium)
                }

                end.linkTo(parent.end, if (verticalContent) 0.dp else spaceMedium)
                top.linkTo(parent.top, if (verticalContent) 0.dp else spaceMedium)
                bottom.linkTo(secondaryButtons.top, spaceMedium)

                width = Dimension.fillToConstraints
                height = Dimension.fillToConstraints
            },
            currentExpression = uiState.currentExpression,
            result = result,
            angleType = uiState.angleType,
            updateTextFieldValue = { value ->
                onEvent(HomeUiEvent.UpdateTextFieldValue(value))
            },
            shape = if (verticalContent) RoundedCornerShape(
                bottomStart = 20.dp,
                bottomEnd = 20.dp
            ) else RoundedCornerShape(20.dp),
        )

        ButtonGrid(
            modifier = Modifier.constrainAs(primaryButtons) {
                if (verticalContent) {
                    top.linkTo(secondaryButtons.bottom)
                    start.linkTo(parent.start, spaceMedium)
                } else {
                    top.linkTo(expressionContent.bottom, spaceMedium)
                    start.linkTo(secondaryButtons.end, spaceMedium)
                }

                end.linkTo(parent.end, spaceMedium)
                bottom.linkTo(parent.bottom, spaceMedium)

                width = Dimension.fillToConstraints
                height = Dimension.fillToConstraints
            },
            isPortrait = verticalContent,
            onActionClick = { action ->
                onEvent(HomeUiEvent.OnButtonActionClick(action))
            },
        )

        SecondaryButtonGrid(
            modifier = Modifier.constrainAs(secondaryButtons) {
                if (showLeftExpressionHistory && !verticalContent) {
                    start.linkTo(historyComponent.end, spaceMedium)
                } else {
                    start.linkTo(parent.start, spaceMedium)
                }

                top.linkTo(expressionContent.bottom)

                if (verticalContent) {
                    end.linkTo(parent.end, spaceMedium)
                    bottom.linkTo(primaryButtons.top, spaceMedium)
                    width = Dimension.fillToConstraints
                    height = Dimension.wrapContent
                } else {
                    bottom.linkTo(parent.bottom, spaceMedium)
                    width = Dimension.wrapContent
                    height = Dimension.fillToConstraints
                }
            },
            isPortrait = verticalContent,
            buttonGridExpanded = uiState.moreActionsExpanded,
            angleType = uiState.angleType,
            isInverse = uiState.isInverse,
            onActionClick = { action ->
                onEvent(HomeUiEvent.OnButtonActionClick(action))
            },
            onMoreActionsClick = { onEvent(HomeUiEvent.OnChangeMoreActionsClick) }
        )
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
