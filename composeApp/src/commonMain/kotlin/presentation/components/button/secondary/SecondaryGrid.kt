package presentation.components.button.secondary

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import core.ButtonAction
import core.presentation.theme.spacing
import model.AngleType

@Composable
internal fun SecondaryButtonGrid(
    modifier: Modifier = Modifier,
    isPortrait: Boolean,
    buttonGridExpanded: Boolean,
    angleType: AngleType,
    isInverse: Boolean,
    onActionClick: (action: ButtonAction) -> Unit,
    onMoreActionsClick: () -> Unit
) {
    val actions = remember(angleType, isInverse) {
        ButtonAction.getAllSecondaryButtons(
            angleType = angleType,
            isInverse = isInverse
        )
    }

    SecondaryButtonGrid(
        modifier = modifier,
        isPortrait = isPortrait,
        actions = actions,
        buttonGridExpanded = buttonGridExpanded,
        onActionClick = onActionClick,
        onMoreActionsClick = onMoreActionsClick
    )
}

@Composable
private fun SecondaryButtonGrid(
    modifier: Modifier = Modifier,
    isPortrait: Boolean,
    actions: List<ButtonAction>,
    buttonGridExpanded: Boolean,
    onActionClick: (action: ButtonAction) -> Unit,
    onMoreActionsClick: () -> Unit
) {
    val topFixedActions = remember(actions) { actions.take(4) }
    val gridActions = remember(actions) { actions.drop(4) }

    if (isPortrait) {
        VerticalItemsGrid(
            modifier = modifier,
            topFixedActions = topFixedActions,
            gridActions = gridActions,
            buttonGridExpanded = buttonGridExpanded,
            onActionClick = onActionClick,
            onMoreActionsClick = onMoreActionsClick
        )
    } else {
        HorizontalItemsGrid(
            modifier = modifier,
            topFixedActions = topFixedActions,
            gridActions = gridActions,
            buttonGridExpanded = buttonGridExpanded,
            onActionClick = onActionClick,
            onMoreActionsClick = onMoreActionsClick
        )
    }
}

@Composable
private fun VerticalItemsGrid(
    modifier: Modifier = Modifier,
    topFixedActions: List<ButtonAction>,
    gridActions: List<ButtonAction>,
    buttonGridExpanded: Boolean,
    onActionClick: (action: ButtonAction) -> Unit,
    onMoreActionsClick: () -> Unit
) {
    Column(modifier = modifier) {
        VerticalFixedItems(
            modifier = Modifier.fillMaxWidth(),
            actions = topFixedActions,
            buttonGridExpanded = buttonGridExpanded,
            onActionClick = onActionClick,
            onMoreActionsClick = onMoreActionsClick
        )

        AnimatedVisibility(visible = buttonGridExpanded) {
            LazyVerticalGrid(
                modifier = Modifier.fillMaxWidth(1f).padding(end = VERTICAL_GRID_RIGHT_PADDING),
                columns = GridCells.Fixed(count = 4),
            ) {
                items(items = gridActions) { action ->
                    SecondaryButtonComponent(
                        buttonAction = action,
                        modifier = Modifier.fillMaxWidth().height(VERTICAL_ITEM_HEIGHT),
                        onClick = { onActionClick(action) }
                    )
                }
            }
        }
    }
}

@Composable
private fun VerticalFixedItems(
    modifier: Modifier = Modifier,
    actions: List<ButtonAction>,
    buttonGridExpanded: Boolean,
    onActionClick: (action: ButtonAction) -> Unit,
    onMoreActionsClick: () -> Unit
) {
    check(actions.size == 4) { "TopFixedItems requires exactly 4 actions" }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        actions.forEach { action ->
            SecondaryButtonComponent(
                buttonAction = action,
                modifier = Modifier.weight(1f).height(VERTICAL_ITEM_HEIGHT),
                onClick = { onActionClick(action) }
            )
        }

        MoreSecondaryActionsItem(
            buttonGridExpanded = buttonGridExpanded,
            verticalContent = false,
            onClick = onMoreActionsClick
        )
    }
}

@Composable
private fun HorizontalItemsGrid(
    modifier: Modifier = Modifier,
    topFixedActions: List<ButtonAction>,
    gridActions: List<ButtonAction>,
    buttonGridExpanded: Boolean,
    onActionClick: (action: ButtonAction) -> Unit,
    onMoreActionsClick: () -> Unit
) {
    val spaceSmall = MaterialTheme.spacing.small

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium)
    ) {
        Surface(
            modifier = Modifier.fillMaxHeight().width(HORIZONTAL_ITEM_WIDTH),
            shape = MaterialTheme.shapes.extraLarge,
            tonalElevation = 4.dp
        ) {
            HorizontalFixedItems(
                modifier = Modifier.fillMaxHeight(),
                actions = topFixedActions,
                buttonGridExpanded = buttonGridExpanded,
                onActionClick = onActionClick,
                onMoreActionsClick = onMoreActionsClick
            )
        }

        AnimatedVisibility(visible = buttonGridExpanded) {
            LazyHorizontalGrid(
                modifier = Modifier.fillMaxHeight(),
                rows = GridCells.Fixed(count = 4),
                verticalArrangement = Arrangement.spacedBy(spaceSmall),
                horizontalArrangement = Arrangement.spacedBy(spaceSmall),
            ) {
                items(items = gridActions) { action ->
                    SecondaryButtonComponent(
                        buttonAction = action,
                        modifier = Modifier.fillMaxHeight().width(HORIZONTAL_ITEM_WIDTH),
                        onClick = { onActionClick(action) }
                    )
                }
            }
        }
    }
}

@Composable
private fun HorizontalFixedItems(
    modifier: Modifier = Modifier,
    actions: List<ButtonAction>,
    buttonGridExpanded: Boolean,
    onActionClick: (action: ButtonAction) -> Unit,
    onMoreActionsClick: () -> Unit
) {
    check(actions.size == 4) { "TopFixedItems requires exactly 4 actions" }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        actions.forEach { action ->
            SecondaryButtonComponent(
                buttonAction = action,
                modifier = Modifier.weight(1f).fillMaxWidth(),
                onClick = { onActionClick(action) }
            )
        }

        MoreSecondaryActionsItem(
            buttonGridExpanded = buttonGridExpanded,
            verticalContent = true,
            onClick = onMoreActionsClick
        )
    }
}

@Composable
internal fun MoreSecondaryActionsItem(
    modifier: Modifier = Modifier,
    buttonGridExpanded: Boolean,
    verticalContent: Boolean,
    onClick: () -> Unit
) {
    val rotation = when {
        buttonGridExpanded && verticalContent -> VERTICAL_EXPANDED_ROTATION
        buttonGridExpanded && !verticalContent -> HORIZONTAL_EXPANDED_ROTATION
        !buttonGridExpanded && !verticalContent -> HORIZONTAL_NORMAL_ROTATION
        else -> VERTICAL_NORMAL_ROTATION
    }

    val rotationAnimated = animateFloatAsState(
        targetValue = rotation,
        label = "More Actions Rotation"
    )

    Surface(
        modifier = modifier,
        shape = CircleShape,
        tonalElevation = 8.dp,
        onClick = onClick
    ) {
        Icon(
            imageVector = Icons.Rounded.ArrowDropDown,
            contentDescription = "More Actions",
            modifier = Modifier
                .padding(MaterialTheme.spacing.extraSmall)
                .graphicsLayer {
                    rotationZ = rotationAnimated.value
                }
        )
    }
}

private val VERTICAL_ITEM_HEIGHT = 48.dp

/**
 * Padding for the right side of the vertical grid to align with the top fixed items
 */
private val VERTICAL_GRID_RIGHT_PADDING = 48.dp
private val HORIZONTAL_ITEM_WIDTH = 64.dp

private const val VERTICAL_NORMAL_ROTATION = 270f
private const val VERTICAL_EXPANDED_ROTATION = 90f
private const val HORIZONTAL_NORMAL_ROTATION = 0f
private const val HORIZONTAL_EXPANDED_ROTATION = 180f
