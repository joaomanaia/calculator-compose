package com.infinitepower.calculator.compose.ui.components.button.secondary

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowDropDown
import androidx.compose.material.icons.rounded.ArrowDropUp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.infinitepower.calculator.compose.ui.components.button.ButtonAction
import com.infinitepower.calculator.compose.ui.theme.CalculatorTheme
import com.infinitepower.calculator.compose.ui.theme.spacing

@Composable
@OptIn(ExperimentalMaterial3Api::class)
internal fun SecondaryButtonGrid(
    modifier: Modifier = Modifier,
    isPortrait: Boolean,
    buttonGridExpanded: Boolean,
    onActionClick: (action: ButtonAction) -> Unit,
    onMoreActionsClick: (expanded: Boolean) -> Unit
) {
    val actions = ButtonAction.getAllSecondaryButtons()

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
@ExperimentalMaterial3Api
private fun SecondaryButtonGridImpl(
    modifier: Modifier = Modifier,
    isPortrait: Boolean,
    actions: List<ButtonAction>,
    buttonGridExpanded: Boolean,
    onActionClick: (action: ButtonAction) -> Unit,
    onMoreActionsClick: (expanded: Boolean) -> Unit
) {
    val spaceSmall = MaterialTheme.spacing.small
    val spaceMedium = MaterialTheme.spacing.medium

    val gridActions = if (buttonGridExpanded) actions else actions.take(4)

    Row(
        verticalAlignment = Alignment.Top,
        modifier = modifier.padding(end = spaceMedium)
    ) {
        LazyVerticalGrid(
            modifier = Modifier.weight(1f),
            columns = GridCells.Fixed(count = if (isPortrait) 4 else 3),
            verticalArrangement = Arrangement.spacedBy(spaceSmall),
            horizontalArrangement = Arrangement.spacedBy(spaceSmall),
            contentPadding = PaddingValues(
                horizontal = if (isPortrait) spaceMedium else 0.dp,
                vertical = spaceSmall
            )
        ) {
            items(items = gridActions) { action ->
                SecondaryButtonComponent(
                    buttonAction = action,
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { onActionClick(action) }
                )
            }
        }
        if (isPortrait) {
            MoreSecondaryActionsItem(
                modifier = Modifier.padding(top = spaceSmall),
                buttonGridExpanded = buttonGridExpanded,
                onClick = {
                    onMoreActionsClick(!buttonGridExpanded)
                }
            )
        }
    }
}

@Composable
@ExperimentalMaterial3Api
private fun MoreSecondaryActionsItem(
    modifier: Modifier = Modifier,
    buttonGridExpanded: Boolean,
    onClick: () -> Unit
) {
    Surface(
        modifier = modifier,
        shape = CircleShape,
        tonalElevation = 8.dp,
        onClick = onClick
    ) {
        Icon(
            imageVector = if (buttonGridExpanded) Icons.Rounded.ArrowDropUp else Icons.Rounded.ArrowDropDown,
            contentDescription = "More Actions",
            modifier = Modifier.padding(MaterialTheme.spacing.extraSmall)
        )
    }
}

@Composable
@Preview(
    showBackground = true,
    group = "Button Grid"
)
@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    group = "Button Grid"
)
private fun ButtonGridPreview() {
    CalculatorTheme {
        Surface {
            SecondaryButtonGrid(
                modifier = Modifier.fillMaxWidth(),
                isPortrait = true,
                buttonGridExpanded = true,
                onActionClick = {},
                onMoreActionsClick = {}
            )
        }
    }
}

@Composable
@Preview(
    showBackground = true,
    group = "More Secondary Actions Item"
)
@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    group = "More Secondary Actions Item"
)
@OptIn(ExperimentalMaterial3Api::class)
private fun MoreSecondaryActionsItemPreview() {
    CalculatorTheme {
        Surface {
            MoreSecondaryActionsItem(
                onClick = {},
                buttonGridExpanded = false
            )
        }
    }
}