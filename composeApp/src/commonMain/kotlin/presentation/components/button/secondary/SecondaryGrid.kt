package presentation.components.button.secondary

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp
import core.ButtonAction
import core.presentation.theme.CalculatorTheme
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

    SecondaryButtonGridImpl(
        modifier = modifier,
        isPortrait = isPortrait,
        actions = actions,
        buttonGridExpanded = buttonGridExpanded,
        onActionClick = onActionClick,
        onMoreActionsClick = onMoreActionsClick
    )
}

@Composable
private fun SecondaryButtonGridImpl(
    modifier: Modifier = Modifier,
    isPortrait: Boolean,
    actions: List<ButtonAction>,
    buttonGridExpanded: Boolean,
    onActionClick: (action: ButtonAction) -> Unit,
    onMoreActionsClick: () -> Unit
) {
    val spaceSmall = MaterialTheme.spacing.small

    val topFixedActions = remember(actions) { actions.take(4) }
    val gridActions = remember(actions) { actions.drop(4) }

    Row(
        verticalAlignment = Alignment.Top,
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            TopFixedItems(
                modifier = Modifier.fillMaxWidth(),
                actions = topFixedActions,
                onActionClick = onActionClick
            )

            AnimatedVisibility(visible = buttonGridExpanded) {
                LazyVerticalGrid(
                    modifier = Modifier.fillMaxWidth(1f),
                    columns = GridCells.Fixed(count = if (isPortrait) 4 else 3),
                    verticalArrangement = Arrangement.spacedBy(spaceSmall),
                    horizontalArrangement = Arrangement.spacedBy(spaceSmall),
                ) {
                    items(items = gridActions) { action ->
                        SecondaryButtonComponent(
                            buttonAction = action,
                            modifier = Modifier.fillMaxWidth(),
                            onClick = { onActionClick(action) }
                        )
                    }
                }
            }
        }
        if (isPortrait) {
            MoreSecondaryActionsItem(
                modifier = Modifier.padding(
                    end = spaceSmall,
                    top = spaceSmall
                ),
                buttonGridExpanded = buttonGridExpanded,
                onClick = onMoreActionsClick
            )
        }
    }
}

@Composable
private fun TopFixedItems(
    modifier: Modifier = Modifier,
    actions: List<ButtonAction>,
    onActionClick: (action: ButtonAction) -> Unit
) {
    check(actions.size == 4 ) { "TopFixedItems requires exactly 4 actions" }

    Row(
        verticalAlignment = Alignment.Top,
        modifier = modifier
    ) {
        actions.forEach { action ->
            SecondaryButtonComponent(
                buttonAction = action,
                modifier = Modifier.weight(1f),
                onClick = { onActionClick(action) }
            )
        }
    }
}

@Composable
internal fun MoreSecondaryActionsItem(
    modifier: Modifier = Modifier,
    buttonGridExpanded: Boolean,
    onClick: () -> Unit
) {
    // When the button grid is expanded, the icon should rotate 180 degrees
    val rotation = animateFloatAsState(
        targetValue = if (buttonGridExpanded) 180f else 0f,
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
                .rotate(rotation.value)
        )
    }
}